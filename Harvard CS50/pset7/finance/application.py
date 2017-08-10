from cs50 import SQL
from flask import Flask, flash, redirect, render_template, request, session, url_for
from flask_session import Session
from passlib.apps import custom_app_context as pwd_context
from tempfile import gettempdir
from helpers import *

# configure application
app = Flask(__name__)

# ensure responses aren't cached
if app.config["DEBUG"]:
    @app.after_request
    def after_request(response):
        response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
        response.headers["Expires"] = 0
        response.headers["Pragma"] = "no-cache"
        return response

# custom filter
app.jinja_env.filters["usd"] = usd

# configure session to use filesystem (instead of signed cookies)
app.config["SESSION_FILE_DIR"] = gettempdir()
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

# configure CS50 Library to use SQLite database
db = SQL("sqlite:///finance.db")

@app.route("/")
@login_required
def index():
    user_id = session['user_id']
    
    owned_stocks = db.execute("""SELECT amount, ticker FROM stocks_owned
                                   INNER JOIN users ON user_id = users.id 
                                   INNER JOIN stocks ON stock_id = stocks.id
                                 WHERE user_id = :user_id""", user_id=user_id)
                
    user_info = db.execute("SELECT username, cash FROM users WHERE id = :user_id", user_id=user_id)[0]
    username = user_info['username']
    cash = "{:20,.2f}".format(user_info['cash'])
    net_portfolio = 0
    
    for row in owned_stocks:
        print(row)
        stock = lookup(row['ticker'])
        row['company_name'] = stock['name']
        row['price'] = stock['price']
        row['shares'] = row['amount']
        row['total'] = row['shares'] * stock['price']
        net_portfolio += row['total']
        row['total'] = "{:20,.2f}".format(row['total'])
        row['price'] = "{:20,.2f}".format(row['price'])
        

    net_portfolio = "{:20,.2f}".format(net_portfolio)
    
    return render_template('index.html', info=owned_stocks, cash=cash, username=username, net_portfolio=net_portfolio)

@app.route("/buy", methods=["GET", "POST"])
@login_required
def buy():
    """Buy shares of stock."""
    if request.method == "POST":
        ticker = request.form.get("ticker").strip('$').upper()
        stock = lookup(ticker) 
        
        # check if stock exists
        if stock == None:
            return apology("Ticker does not exist!")
            
        amount = intTryParse(request.form.get("amount"))
        
        if amount[1] == False:
            return apology("Please input a valid number")
        amount = amount[0]
        
        user_id = session['user_id']
        user = db.execute("SELECT cash, username FROM 'users' WHERE id = :user_id", user_id=user_id)
        cash = user[0]['cash']
        username = user[0]['username']
        stock_price = stock['price']
        purchase_price = stock_price * amount
        
        # check if enough money
        if purchase_price > cash:
            return apology("Not enough cash", "Can't Buy Stockz")
            
        # make transaction
        stock_id = db.execute("SELECT id FROM stocks WHERE ticker=:ticker", ticker=ticker)
        
        # subtract cash from users account
        new_cash = cash - purchase_price
        db.execute("UPDATE 'users' SET cash = ':cash' WHERE id = ':user_id'", cash=new_cash, user_id=user_id)
        
        # manage making the purchase 
        if stock_id:
            stock_id = stock_id[0]['id']
        else:
            # insert stock into stock table
            db.execute("INSERT INTO stocks ('ticker') VALUES (:ticker)", ticker=ticker)
            
            # select stock id from stock table       
            stock_id = db.execute("SELECT id FROM stocks WHERE ticker=:ticker", ticker=ticker)[0]['id']
        
        # insert purchase
        db.execute("""INSERT INTO 'purchases' ('user_id', 'stock_id', 'price', 'amount', 'date')
                      VALUES (':user_id', ':stock_id', ':price', ':amount', CURRENT_TIMESTAMP)""",
                      user_id=user_id, stock_id=stock_id, price=stock_price, amount=amount)
                      
        # check if user owns any stocks
        stock_owned = db.execute("""SELECT amount FROM stocks_owned 
                                        INNER JOIN stocks ON stock_id = stocks.id
                                        INNER JOIN users on user_id = users.id
                                    WHERE user_id=:user_id
                                        AND ticker=:ticker""", user_id=user_id, ticker=ticker)
        if stock_owned:
            # update stock info
            amount += stock_owned[0]['amount'] # add amount to amount user wants to purchase
            db.execute("""UPDATE stocks_owned SET amount = :amount
                            WHERE user_id = :user_id AND stock_id = :stock_id""",
                            user_id=user_id, stock_id=stock_id, amount=amount)
        else:
            # insert ownership into stock table
            db.execute("""INSERT INTO stocks_owned (user_id, stock_id, amount)
                          VALUES(:user_id, :stock_id, :amount)""",
                          user_id=user_id, stock_id=stock_id, amount=amount)
            
        flash("Successfully purchased " + stock['name'])
        return redirect(url_for("index"))
    return render_template('buy.html')

@app.route("/history")
@login_required
def history():
    """Show history of transactions."""
    user_id = session['user_id']
    info = db.execute("""SELECT ticker, price, amount, date FROM purchases
                           INNER JOIN stocks ON stock_id = stocks.id
                           INNER JOIN users ON user_id = users.id
                         WHERE user_id = :user_id
                         ORDER BY date DESC""", user_id=user_id)
    return render_template('history.html', info=info)

@app.route("/login", methods=["GET", "POST"])
def login():
    """Log user in."""

    # forget any user_id
    session.clear()

    # if user reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

        # ensure username was submitted
        if not request.form.get("username"):
            return apology("must provide username")

        # ensure password was submitted
        elif not request.form.get("password"):
            return apology("must provide password")

        # query database for username
        rows = db.execute("SELECT * FROM users WHERE username = :username", username=request.form.get("username"))

        # ensure username exists and password is correct
        if len(rows) != 1 or not pwd_context.verify(request.form.get("password"), rows[0]["hash"]):
            return apology("invalid username and/or password")

        # remember which user has logged in
        session["user_id"] = rows[0]["id"]

        # redirect user to home page
        return redirect(url_for("index"))

    # else if user reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("login.html")

@app.route("/logout")
def logout():
    """Log user out."""

    # forget any user_id
    session.clear()

    # redirect user to login form
    return redirect(url_for("login"))

@app.route("/quote", methods=["GET", "POST"])
@login_required
def quote():
    """Get stock quote."""
    if request.method == "POST":
        ticker = request.form.get("ticker")
        stock_info = lookup(ticker) # gets price, company and ticker of a company
        if(stock_info): # returns None object if couldn't find ticker
            return render_template('quote.html', quote=stock_info)
        return apology("Ticker does not exist!")
    return render_template('quote.html')

@app.route("/register", methods=["GET", "POST"])
def register():
    """Register user."""
    if request.method == 'POST':
        username = request.form.get("username")
        password = request.form.get("password")
        password_confirmation = request.form.get("password_confirmation") 
        hashed_password = pwd_context.encrypt(password)
        
        if username == "" or password == "" or password_confirmation == "":
            return apology("Cannot have blank fields.")
        elif password != password_confirmation:
            return apology("Passwords don't match!")
        else:
            result = db.execute("INSERT INTO 'users' ('username', 'hash') VALUES (:username, :hash)", username=username, hash=hashed_password)
            if(result):
                # select new user from table and set the session user_id to it
                rows = db.execute("SELECT * FROM users WHERE username = :username", username=username)
                session["user_id"] = rows[0]["id"]
                return apology("TODO")
            return apology("User already exists!")
    else:
        return redirect(url_for('index'))

@app.route("/sell", methods=["GET", "POST"])
@login_required
def sell():
    """Sell shares of stock."""
    if request.method == 'POST':
        ticker = request.form.get('ticker').strip("$").upper()
        amount_to_sell = intTryParse(request.form.get('amount'))
        if amount_to_sell[1] == False:
            return apology("Please input a valid number")
            
        amount_to_sell = amount_to_sell[0]
        
        user_id = session['user_id']
        
        # check if amount is greater than 0
        if amount_to_sell < 1:
            return apology("Please input a valid number")
            
        # lookup the stock
        stock = lookup(ticker)
        
        if stock == None:
            return apology("Stock does not exist")
        
        user_share = db.execute("""SELECT amount, stock_id, cash FROM stocks_owned
                                      INNER JOIN stocks ON stocks.id = stock_id
                                      INNER JOIN users ON users.id = user_id
                                    WHERE user_id = :user_id
                                      AND ticker = :ticker""",
                                    user_id=user_id, ticker=ticker)
        if not user_share:
            return apology("You don't own that stock!")
            
        user_share = user_share[0] # only expecting one row
        
        if user_share['amount'] < amount_to_sell:
            return apology("You don't have that many to sell!")
            
        
        stock_id = user_share['stock_id']
        profit = stock['price'] * amount_to_sell
        new_amount = user_share['amount'] - amount_to_sell
        
        
        if new_amount == 0:
            # TODO: if user shares is 0 delete from purchases table 
            db.execute("DELETE FROM stocks_owned WHERE user_id=:user_id AND stock_id=:stock_id", user_id=user_id, stock_id=stock_id)
        else:
            # update the stocks owned table
            db.execute("""UPDATE stocks_owned
                            SET amount=:amount
                          WHERE user_id = :user_id
                            AND stock_id = :stock_id""", 
                            user_id=user_id, stock_id=stock_id, amount=new_amount)
                            
        new_cash = user_share['cash'] + profit
        
        db.execute("UPDATE users SET cash=:cash WHERE id=:user_id", cash=new_cash, user_id=user_id)
        
        flash('Successfully sold {} of ${}'.format(amount_to_sell, ticker))
        
        return redirect(url_for('index'))
    return render_template('sell.html')

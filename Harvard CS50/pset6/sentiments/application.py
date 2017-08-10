from flask import Flask, redirect, render_template, request, url_for

import sys
import os
import helpers

from analyzer import Analyzer
from helpers import get_user_timeline
from nltk.tokenize import TweetTokenizer

app = Flask(__name__)

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/search")
def search():

    # validate screen_name
    screen_name = request.args.get("screen_name", "").lstrip("@")
    if not screen_name:
        return redirect(url_for("index"))

    # get screen_name's tweets
    tweets = helpers.get_user_timeline(screen_name)

    # init analyzer
    positives = os.path.join(sys.path[0], "positive-words.txt")
    negatives = os.path.join(sys.path[0], "negative-words.txt")

    # instantiate analyzer
    analyzer = Analyzer(positives, negatives)
    
    tokenize = TweetTokenizer().tokenize
    
    positive, negative, neutral = 0.0, 0.0, 0.0
    
    user_timeline = get_user_timeline(screen_name, 100)
    
    if user_timeline is None:
        exit("Could not find user, please try a different @ name!")
        
    for tweet in user_timeline:
        score = 0
        for word in tokenize(tweet):
            score += analyzer.analyze(word)
        if score > 0: 
            positive+=1
        elif score < 0:
            negative+=1
        else:
            neutral+=1
    
    

    # generate chart
    chart = helpers.chart(positive, negative, neutral)

    # render results
    return render_template("search.html", chart=chart, screen_name=screen_name)

package observatory

/**
  * 6th (and last) milestone: user interface polishing
  */
object Interaction2 {

  /**
    * @return The available layers of the application
    */
  def availableLayers: Seq[Layer] = {
    Seq(Layer(LayerName.Temperatures, Seq(), 1975 to 2015), Layer(LayerName.Deviations, Seq(), 1975 to 2015))
  }

  /**
    * @param selectedLayer A signal carrying the layer selected by the user
    * @return A signal containing the year bounds corresponding to the selected layer
    */
  def yearBounds(selectedLayer: Signal[Layer]): Signal[Range] = {
    Signal(selectedLayer().bounds)
  }

  /**
    * @param selectedLayer The selected layer
    * @param sliderValue The value of the year slider
    * @return The value of the selected year, so that it never goes out of the layer bounds.
    *         If the value of `sliderValue` is out of the `selectedLayer` bounds,
    *         this method should return the closest value that is included
    *         in the `selectedLayer` bounds.
    */
  def yearSelection(selectedLayer: Signal[Layer], sliderValue: Signal[Year]): Signal[Year] = {
    val bounds = Signal(yearBounds(selectedLayer)().toList)
    Signal(
      if(bounds().contains(sliderValue())) sliderValue()
      else {
        val (min, max) = (bounds().min, bounds().max)
        if(sliderValue() > max) max else min
      }
    )
  }

  def main(args: Array[String]): Unit = {
    val year = Signal(2000)

    val res = yearSelection(Signal(availableLayers.head), year)

//    println(res())

  }

  /**
    * @param selectedLayer The selected layer
    * @param selectedYear The selected year
    * @return The URL pattern to retrieve tiles
    */
  def layerUrlPattern(selectedLayer: Signal[Layer], selectedYear: Signal[Year]): Signal[String] = {
    Signal(s"target/${selectedLayer().layerName}/${yearSelection(selectedLayer, selectedYear)()}/${selectedLayer().layerName.id}.png")
  }

  /**
    * @param selectedLayer The selected layer
    * @param selectedYear The selected year
    * @return The caption to show
    */
  def caption(selectedLayer: Signal[Layer], selectedYear: Signal[Year]): Signal[String] = {
    Signal(s"${selectedLayer().layerName} (${yearSelection(selectedLayer, selectedYear)()})")
  }
}

sealed abstract class LayerName(val id: String)
object LayerName {
  case object Temperatures extends LayerName("temperatures")
  case object Deviations extends LayerName("deviations")
}

/**
  * @param layerName Name of the layer
  * @param colorScale Color scale used by the layer
  * @param bounds Minimum and maximum year supported by the layer
  */
case class Layer(layerName: LayerName, colorScale: Seq[(Temperature, Color)], bounds: Range)

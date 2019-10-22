# Animated Line Chart

This library allows you to create animated line chart and adding image on set points

  ![](fig.gif)

## Quickstart

Step 1. Add the JitPack repository to your build file

```bash
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```bash
	dependencies {
	        implementation 'com.github.darkh:AnimatedLineChart:Tag'
	}
```


## Usage

First, you have to add the View in your xml file
```bash
<com.gadnimation.animatedlinecharts.AnimatedLineChart
       android:id="@+id/charView"
       android:layout_width="match_parent"
       android:layout_height="200dp"
       app:steps="10"
       app:layout_constraintBottom_toBottomOf="parent"
       app:layout_constraintTop_toTopOf="parent"
       app:layout_constraintVertical_bias="0.13999999"
       tools:layout_editor_absoluteX="6dp" />
```

then in your Activity 
```bash
AnimatedLineChart chart = findViewById(R.id.charView);
        ArrayList<DataSet> points = new ArrayList<>();
        points.add(new DataSet(40,"2016",bitmap));
        points.add(new DataSet(50,"2017",bitmap));
        points.add(new DataSet(20,"2018",bitmap));
        points.add(new DataSet(60,"2019",bitmap));
        points.add(new DataSet(70,"2020",bitmap));

        chart.setData(points);
```

The data set include 3 objects: 
- the point
- point name
- point image as a bitmap

you can change the view characteristics as follow: 

```bash
 chart.setLineColor(Color.BLUE); // set the color for the animated line

 chart.setLegendsFrameColor(Color.BLUE); // set the color for chart frame

 chart.setAnimationDuration(2000); // change the speed of animation

 chart.addImageBorder(true); // adding border for the point image

 chart.setImageBorderColor(Color.BLUE) // set the color for image border 

 chart.setImageBorderStrokeWidth(15f); // set the width for image border stroke

 chart.setLegendsPathEffect(pathEffect); // set pathEffect for chart frame

 chart.setLineStrokeWidth(5f); set the animated line storke width

 chart.setDrawLegends(false); by default the chart frame is true but you can disable it by set false

 chart.setBackgroundColor(alpha, red, green, blue); set background color for the chart

 chart.setTextSize(20); set text size for labels

 chart.setRepeat (true) by default false but you can set it true so the animation will keep repeating 
```


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


## License
Apache License 2.0
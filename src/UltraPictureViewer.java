import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class UltraPictureViewer extends Application {
	
	ColorAdjust colorAdjust = new ColorAdjust(); 
			
	public static void main(String[] args) {
		launch(args);		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane mainPane = new BorderPane();
		
		ScrollPane mainCanvasHolder = new ScrollPane();
		ImageView mainCanvas = new ImageView();
		mainCanvasHolder.setContent(mainCanvas);
		mainCanvasHolder.setPrefSize(1024, 690);
		mainCanvas.setCursor(Cursor.CROSSHAIR);
		mainPane.setCenter(mainCanvasHolder);
		
		Pane colorPane = new Pane();
		
		Label lblBrightness = new Label("Brightness:");
		lblBrightness.setLayoutX(0);
		lblBrightness.setLayoutY(0);
		colorPane.getChildren().add(lblBrightness);
				
		Slider sliderBrightness = new Slider(-1, 1, 0);
		sliderBrightness.setShowTickMarks(true);
		sliderBrightness.setShowTickLabels(true);
		sliderBrightness.setMajorTickUnit(0.5f);
		sliderBrightness.setBlockIncrement(0.1f);
		sliderBrightness.setLayoutX(0);
		sliderBrightness.setLayoutY(15);				
		colorPane.getChildren().add(sliderBrightness);
		
		sliderBrightness.valueProperty().addListener(new ChangeListener<Number>() {
	         public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
	        	 colorAdjust.setBrightness((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	          }
	    });
		
		Label lblContrast = new Label("Contrast:");
		lblContrast.setLayoutX(0);
		lblContrast.setLayoutY(60);
		colorPane.getChildren().add(lblContrast);
				
		Slider sliderContrast = new Slider(-1, 1, 0);
		sliderContrast.setShowTickMarks(true);
		sliderContrast.setShowTickLabels(true);
		sliderContrast.setMajorTickUnit(0.5f);
		sliderContrast.setBlockIncrement(0.1f);
		sliderContrast.setLayoutX(0);
		sliderContrast.setLayoutY(75);				
		colorPane.getChildren().add(sliderContrast);
		
		sliderContrast.valueProperty().addListener(new ChangeListener<Number>() {
	         public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
	        	 colorAdjust.setContrast((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	          }
	    });
		
		Label lblHue = new Label("Hue:");
		lblHue.setLayoutX(0);
		lblHue.setLayoutY(120);
		colorPane.getChildren().add(lblHue);
				
		Slider sliderHue = new Slider(-1, 1, 0);
		sliderHue.setShowTickMarks(true);
		sliderHue.setShowTickLabels(true);
		sliderHue.setMajorTickUnit(0.5f);
		sliderHue.setBlockIncrement(0.1f);
		sliderHue.setLayoutX(0);
		sliderHue.setLayoutY(135);				
		colorPane.getChildren().add(sliderHue);
		
		sliderHue.valueProperty().addListener(new ChangeListener<Number>() {
	         public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
	        	 colorAdjust.setHue((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	          }
	    });
		
		Label lblSaturation = new Label("Saturation:");
		lblSaturation.setLayoutX(0);
		lblSaturation.setLayoutY(180);
		colorPane.getChildren().add(lblSaturation);
				
		Slider sliderSaturation = new Slider(-1, 1, 0);
		sliderSaturation.setShowTickMarks(true);
		sliderSaturation.setShowTickLabels(true);
		sliderSaturation.setMajorTickUnit(0.5f);
		sliderSaturation.setBlockIncrement(0.1f);
		sliderSaturation.setLayoutX(0);
		sliderSaturation.setLayoutY(195);
		colorPane.getChildren().add(sliderSaturation);
		
		sliderSaturation.valueProperty().addListener(new ChangeListener<Number>() {
	         public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
	        	 colorAdjust.setSaturation((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	          }
	    });
		
		Button resetColorAdjust = new Button("Reset Color Adjust");
		resetColorAdjust.setLayoutX(0);
		resetColorAdjust.setLayoutY(250);
		colorPane.getChildren().add(resetColorAdjust);
		
		resetColorAdjust.setOnAction(e->{
			colorAdjust.setBrightness(0);
			sliderBrightness.setValue(0);
			colorAdjust.setContrast(0);
			sliderContrast.setValue(0);
			colorAdjust.setHue(0);
			sliderHue.setValue(0);
			colorAdjust.setSaturation(0);
			sliderSaturation.setValue(0);
		});
		
		Label lblRotationAngle = new Label("Rotation Angle: 0");
		lblRotationAngle.setLayoutX(0);
		lblRotationAngle.setLayoutY(300);
		colorPane.getChildren().add(lblRotationAngle);
		
		Label lblZoomPercent = new Label("Zoom Percent: 100%");
		lblZoomPercent.setLayoutX(0);
		lblZoomPercent.setLayoutY(350);
		colorPane.getChildren().add(lblZoomPercent);
		
		mainPane.setRight(colorPane);
				
		MenuBar menuBar = new MenuBar();
		
		Menu menuFile = new Menu("File");
		
		MenuItem openFile = new MenuItem("Open");
		openFile.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			 
	        //Set extension filter for image files
	        fileChooser.getExtensionFilters().add( new ExtensionFilter("Image Files (*.bmp, *.png, *.jpg, *.gif)", "*.bmp", "*.png", "*.jpg", "*.gif") );
	        
	        //Show open file dialog
	        File file = fileChooser.showOpenDialog(primaryStage);

	        if (file != null) {
	        	Image image = new Image("file:///" + file.getPath());
	        	mainCanvas.setRotate(0);
	        	mainCanvas.setScaleX(1.0);
				mainCanvas.setScaleY(1.0);
				resetColorAdjust.fire();
				lblRotationAngle.setText("Rotation Angle: 0");
				lblZoomPercent.setText("Zoom Percent: 100%");
	    		mainCanvas.setImage(image);
	        }
		});
		
		MenuItem saveFile = new MenuItem("Save");
		saveFile.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			 
	        //Set extension filter for image files
	        fileChooser.getExtensionFilters().addAll( 	new ExtensionFilter("Portable Network Graphics File (*.png)", "*.png") ,	        			
	        											new ExtensionFilter("Graphics Interchange Format File  (*.gif)", "*.gif") );
	        //Set title
	        fileChooser.setTitle("Save as");
	        
	        //Show open file dialog
	        File file = fileChooser.showSaveDialog(primaryStage);
	        WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
	        if (file != null) {
	        	String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
	        	try {
	                ImageIO.write(SwingFXUtils.fromFXImage(newWritableImage, null), fileExtension, file);
	            } catch (IOException ex) {
	            	System.out.println(ex);
	            }     
	        }	        
		});    
				
		MenuItem exitApplication = new MenuItem("Exit");
		exitApplication.setOnAction(e -> {
			primaryStage.close();
		});
		menuFile.getItems().addAll(openFile, saveFile, exitApplication);
						
		Menu menuView = new Menu("View");
		MenuItem rotateRight = new MenuItem("Rotate Right");
		rotateRight.setOnAction(e -> {						
			mainCanvas.setRotate(mainCanvas.getRotate() + 22.5);
			if(mainCanvas.getRotate() > 337.5 )	
				mainCanvas.setRotate(0);
			lblRotationAngle.setText("Rotation Angle: " + mainCanvas.getRotate());
		});
		MenuItem rotateLeft = new MenuItem("Rotate Left");
		rotateLeft.setOnAction(e -> {
			mainCanvas.setRotate(mainCanvas.getRotate() - 22.5);
			if(mainCanvas.getRotate() < -337.5 )	
				mainCanvas.setRotate(0);
			lblRotationAngle.setText("Rotation Angle: " + mainCanvas.getRotate());
		});
		MenuItem zoomIn = new MenuItem("Zoom In");
		zoomIn.setOnAction(e -> {
			mainCanvas.setScaleX(mainCanvas.getScaleX() + 0.1 );
			mainCanvas.setScaleY(mainCanvas.getScaleY() + 0.1 );
			String zoomPercentage = String.format("%.0f", mainCanvas.getScaleX() * 100);
			lblZoomPercent.setText("Zoom Percent: " + zoomPercentage + "%");
		});
		MenuItem zoomOut = new MenuItem("Zoom Out");
		zoomOut.setOnAction(e -> {
			mainCanvas.setScaleX(mainCanvas.getScaleX() - 0.1 );
			mainCanvas.setScaleY(mainCanvas.getScaleY() - 0.1 );
			String zoomPercentage = String.format("%.0f", mainCanvas.getScaleX() * 100);
			lblZoomPercent.setText("Zoom Percent: " + zoomPercentage + "%");
		});
		menuView.getItems().addAll(rotateRight, rotateLeft, zoomIn, zoomOut);
		
		Menu menuHelp = new Menu("Help");
		MenuItem about = new MenuItem("About Ultra Image-Viewer");
		about.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About Ultra Picture-Viewer");
			alert.setHeaderText("Ultra Picure-Viewer");
			alert.setContentText("Ultra Picture-Viewer is an image utility tool.\nThis is Ultra Picture-Viewer version 1.0.\nCreated by Vision Paudel.");
			alert.showAndWait();
		});
		menuHelp.getItems().addAll(about);
			
		menuBar.getMenus().addAll(menuFile, menuView, menuHelp);						
		mainPane.setTop(menuBar);
						
		Scene mainScene = new Scene(mainPane, 1200, 720);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Ultra Picture-Viewer");
		primaryStage.setResizable(false);
		primaryStage.show();
	}		
}
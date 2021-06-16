
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class UltraPictureViewer extends Application {
	
	static ImageView mainCanvas = new ImageView();
	ColorAdjust colorAdjust = new ColorAdjust();
	Rectangle pixelColor = new Rectangle(140,50);
	Label colorValue = new Label("Color: undefined");
		
	Label labelCoordinates = new Label("0, 0 px");
	static int sepia = 0;
	static int invertedColor = 0;
		
	public static void main(String[] args) {
		launch(args);		
	}
		
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane mainPane = new BorderPane();
		
		ScrollPane mainCanvasHolder = new ScrollPane();
		
		mainCanvasHolder.setContent(new Group(mainCanvas));
		mainCanvasHolder.setPrefSize(1024, 690);
		mainCanvas.setCursor(Cursor.CROSSHAIR);
		mainPane.setCenter(mainCanvasHolder);
		
		Pane colorPane = new Pane();
		
		Label lblBrightness = new Label("Brightness: 0.000");
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
	        	 if(invertedColor == 1)
	        		 invertColor();
	        	 if(sepia == 1)
					 sepiaTone();
	        	 colorAdjust.setBrightness((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	        	 String brightnessValue = String.format("%.3f", (double)newValue);
	        	 lblBrightness.setText("Brightness: " + brightnessValue);
	        	 sepia = 0;
	          }
	    });
		
		Label lblContrast = new Label("Contrast: 0.000");
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
	        	 if(invertedColor == 1)
			 		invertColor();
			     if(sepia == 1)
			        sepiaTone();
	        	 colorAdjust.setContrast((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	        	 String contrastValue = String.format("%.3f", (double)newValue);
	        	 lblContrast.setText("Contrast: " + contrastValue);
	        	 sepia = 0;
	          }
	    });
		
		Label lblHue = new Label("Hue: 0.000");
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
	        	 if(invertedColor == 1)
		 			invertColor();
		         if(sepia == 1)
		        	sepiaTone();
	        	 colorAdjust.setHue((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	        	 String hueValue = String.format("%.3f", (double)newValue);
	        	 lblHue.setText("Hue: " + hueValue);
	        	 sepia = 0;
	          }
	    });
		
		Label lblSaturation = new Label("Saturation: 0.000");
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
	        	 if(invertedColor == 1)
	 				invertColor();
	        	 if(sepia == 1)
	        		 sepiaTone(); 
	        	 colorAdjust.setSaturation((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	        	 String saturationValue = String.format("%.3f", (double)newValue);
	        	 lblSaturation.setText("Saturation: " + saturationValue);
	        	 sepia = 0;
	          }
	    });
		
		Button resetColorAdjust = new Button("Reset Color Adjust");
		resetColorAdjust.setLayoutX(0);
		resetColorAdjust.setLayoutY(250);
		colorPane.getChildren().add(resetColorAdjust);
		
		resetColorAdjust.setOnAction(e->{
			colorAdjust.setBrightness(0);
			sliderBrightness.setValue(0);
			lblBrightness.setText("Brightness: 0.000");
			colorAdjust.setContrast(0);
			sliderContrast.setValue(0);
			lblContrast.setText("Contrast: 0.000");
			colorAdjust.setHue(0);
			sliderHue.setValue(0);
			lblHue.setText("Hue: 0.000");
			colorAdjust.setSaturation(0);
			sliderSaturation.setValue(0);
			lblSaturation.setText("Saturation: 0.000");
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
	        fileChooser.getExtensionFilters().addAll( 	new ExtensionFilter("Image Files (*.bmp, *.png, *.jpg, *.gif)", "*.bmp", "*.png", "*.jpg", "*.gif") ,  
	        											new ExtensionFilter("Ultra Picture-Viewer Format File  (*.upvf)", "*.upvf") 	);
	        
	        //Show open file dialog
	        File file = fileChooser.showOpenDialog(primaryStage);
	       
	        	
	        if (file != null) {
	        	String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
	        	SepiaTone sepiaTone = new SepiaTone(); 
			    sepiaTone.setLevel(0); 
			    mainCanvas.setEffect(sepiaTone); 
	        	sepia = 0;
	        	invertedColor = 0;
	        	
	        	if(!fileExtension.equals("upvf")) {
		        	Image image = new Image("file:///" + file.getPath());
		        	mainCanvas.setRotate(0);
		        	mainCanvas.setScaleX(1.0);
					mainCanvas.setScaleY(1.0);
					resetColorAdjust.fire();
					lblRotationAngle.setText("Rotation Angle: 0");
					lblZoomPercent.setText("Zoom Percent: 100%");
		    		mainCanvas.setImage(image);
	        	}
	        	else {
	        		Scanner fileInput;
					try {
						fileInput = new Scanner(file);
						String strWidth = fileInput.nextLine();
		        		int width = Integer.parseInt(strWidth.substring( strWidth.lastIndexOf(":") + 1, strWidth.length() ) );
		        		String strHeight = fileInput.nextLine();
		        		int height = Integer.parseInt(strHeight.substring( strHeight.lastIndexOf(":") + 1, strHeight.length() ) );
		        		WritableImage newWritableImage = new WritableImage(width, height);
		        		PixelWriter newPixelWriter = newWritableImage.getPixelWriter();
		        		
		        		String[] newStringArray = new String[width];
		        		
		        		for(int i = 0; i < height; i++) {		        			
		        			newStringArray = fileInput.nextLine().split(",");		        			
		        			for(int j = 0; j < width; j++) {
		        				Color c = Color.web(newStringArray[j].substring(newStringArray[j].length()-8, newStringArray[j].length()));
		        				newPixelWriter.setColor(j, i, c);
		        			}
		        		}
		        		mainCanvas.setImage(newWritableImage);
		        		
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        		
	        	}
	        }
		});
		
		MenuItem saveFile = new MenuItem("Save");
		saveFile.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			 
	        //Set extension filter for image files
	        fileChooser.getExtensionFilters().addAll( 	new ExtensionFilter("Portable Network Graphics File (*.png)", "*.png") ,	        			
	        											new ExtensionFilter("Graphics Interchange Format File  (*.gif)", "*.gif") , 
	        											new ExtensionFilter("Ultra Picture-Viewer Format File  (*.upvf)", "*.upvf") 	);
	        //Set title
	        fileChooser.setTitle("Save as");
	        
	        //Show open file dialog
	        File file = fileChooser.showSaveDialog(primaryStage);
	        WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
	        if (file != null) {
	        	String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
	        	try {
	        		if(!fileExtension.equals("upvf")) {
	        			ImageIO.write(SwingFXUtils.fromFXImage(newWritableImage, null), fileExtension, file);
	        		}
	        		else {	        			
	        			PrintWriter newPrintWriter = new PrintWriter(file);
	        			int width = (int)newWritableImage.getWidth();
	        			int height = (int)newWritableImage.getHeight();
	        			newPrintWriter.println("width:" + width);
	        			newPrintWriter.println("height:" + height);
	        			PixelReader newPixelReader = newWritableImage.getPixelReader();
	        			for(int i = 0; i < height; i++) {
	        				for(int j = 0; j < width; j++) {
	        					newPrintWriter.print(newPixelReader.getColor(j, i));
	        					if(j < width - 1) {
	        						newPrintWriter.print(",");
	        					}
	        				}
	        				newPrintWriter.println();
	        			}
	        			newPrintWriter.close();
	        		}
	            } catch (IOException ex) {
	            	ex.printStackTrace();
	            }
	        }	        
		});    
				
		MenuItem exitApplication = new MenuItem("Exit");
		exitApplication.setOnAction(e -> {
			primaryStage.close();
		});
		menuFile.getItems().addAll(openFile, saveFile, exitApplication);
		
		Menu menuEdit = new Menu("Edit");	
		MenuItem rotateRight = new MenuItem("Rotate Right");
		rotateRight.setOnAction(e -> {						
			mainCanvas.setRotate(mainCanvas.getRotate() + 22.5);
			if(mainCanvas.getRotate() == 360 )	
				mainCanvas.setRotate(0);
			lblRotationAngle.setText("Rotation Angle: " + mainCanvas.getRotate());
		});
		MenuItem rotateLeft = new MenuItem("Rotate Left");
		rotateLeft.setOnAction(e -> {
			mainCanvas.setRotate(mainCanvas.getRotate() - 22.5);
			if(mainCanvas.getRotate() == -360 )	
				mainCanvas.setRotate(0);
			lblRotationAngle.setText("Rotation Angle: " + mainCanvas.getRotate());
		});
		MenuItem crop = new MenuItem("Crop");
			crop.setOnAction(e -> {
			menuBar.setDisable(true);
			
			Pane cropPane = new Pane();
			
			Scene cropScene = new Scene(cropPane, 600, 250);
			Stage cropStage = new Stage();
			cropStage.setScene(cropScene);
			cropStage.setTitle("Crop Settings");
			cropStage.setResizable(false);
			cropStage.show();
			
			Label originX = new Label("Origin x-coordinate: " + "\u2192");
			originX.setLayoutX(25);
			originX.setLayoutY(20);
			cropPane.getChildren().add(originX);
			
			TextField tfOrginX = new TextField();
			tfOrginX.setLayoutX(180);
			tfOrginX.setLayoutY(20);
			tfOrginX.setMaxWidth(80);
			cropPane.getChildren().add(tfOrginX);
			
			Label originY = new Label("Origin y-coordinate: " + "\u2193");
			originY.setLayoutX(25);
			originY.setLayoutY(60);
			cropPane.getChildren().add(originY);
			
			TextField tfOrginY = new TextField();
			tfOrginY.setLayoutX(180);
			tfOrginY.setLayoutY(60);
			tfOrginY.setMaxWidth(80);
			cropPane.getChildren().add(tfOrginY);
			
			Label cropWidth = new Label("Crop Width: ");
			cropWidth.setLayoutX(25);
			cropWidth.setLayoutY(100);
			cropPane.getChildren().add(cropWidth);
			
			TextField tfWidth = new TextField();
			tfWidth.setLayoutX(180);
			tfWidth.setLayoutY(100);
			tfWidth.setMaxWidth(80);
			cropPane.getChildren().add(tfWidth);
			
			Label cropHeight = new Label("Crop Height: ");
			cropHeight.setLayoutX(25);
			cropHeight.setLayoutY(140);
			cropPane.getChildren().add(cropHeight);
			
			TextField tfHeight = new TextField();
			tfHeight.setLayoutX(180);
			tfHeight.setLayoutY(140);
			tfHeight.setMaxWidth(80);
			cropPane.getChildren().add(tfHeight);
			
			Button btnPreview = new Button("Preview (Fit)");
			btnPreview.setLayoutX(20);
			btnPreview.setLayoutY(200);
			cropPane.getChildren().add(btnPreview);
			
			Button btnPreviewFull = new Button("Preview (Normal)");
			btnPreviewFull.setLayoutX(140);
			btnPreviewFull.setLayoutY(200);
			cropPane.getChildren().add(btnPreviewFull);
			
			Button btnCrop = new Button(" Crop! ");
			btnCrop.setLayoutX(500);
			btnCrop.setLayoutY(200);
			cropPane.getChildren().add(btnCrop);
			
			Button btnCancel = new Button(" Cancel ");
			btnCancel.setLayoutX(420);
			btnCancel.setLayoutY(200);
			cropPane.getChildren().add(btnCancel);
			
			ImageView imageViewCrop = new ImageView();
			ScrollPane cropCanvasHolder = new ScrollPane();			
			cropCanvasHolder.setContent(new Group(imageViewCrop));
			cropCanvasHolder.setPrefSize(300, 180);
			imageViewCrop.setCursor(Cursor.CROSSHAIR);
			cropCanvasHolder.setLayoutX(280);
			cropCanvasHolder.setLayoutY(10);
			
			cropPane.getChildren().add(cropCanvasHolder);
			
			btnPreview.setOnAction(ex->{
				if (mainCanvas.getImage() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Empty Image");
					alert.setContentText("There is no image to preview crop.");
					alert.showAndWait();
				}else {
					
					try {
						int originXCoord = Integer.parseInt( tfOrginX.getText() );
						int originYCoord = Integer.parseInt( tfOrginY.getText() );
						int width = Integer.parseInt( tfWidth.getText() );
						int height = Integer.parseInt( tfHeight.getText() );
						WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
						
						if((width>0) && (height>0) && (originXCoord >= 0 &&  originXCoord < newWritableImage.getWidth()) && (originYCoord >= 0 &&  originYCoord < newWritableImage.getHeight())) {
							
							if((originXCoord + width) > newWritableImage.getWidth())
								width = (int) (newWritableImage.getWidth() - originXCoord);
							
							if((originYCoord + height) > newWritableImage.getHeight())
								height = (int) (newWritableImage.getHeight() - originYCoord);
							
							PixelReader newPixelReader = newWritableImage.getPixelReader();
							WritableImage newImage = new WritableImage(newPixelReader, originXCoord, originYCoord, width, height);
							imageViewCrop.setFitWidth(300);
							imageViewCrop.setFitHeight(180);
							imageViewCrop.setPreserveRatio(true);
							imageViewCrop.setImage(newImage);									
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("OutOfBounds");
							alert.setContentText("Please enter integer coordinates (X & Y) \nwithin bounds. width>0, height>0");
							alert.showAndWait();
						}
					}
					catch(NumberFormatException exception) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("NumberFormatException");
						alert.setContentText("Please enter a valid integer number.");
						alert.showAndWait();
					}
				}
					
					
			});
			
			btnPreviewFull.setOnAction(ex->{
				if (mainCanvas.getImage() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Empty Image");
					alert.setContentText("There is no image to preview crop.");
					alert.showAndWait();
				}else {
					
					try {
						int originXCoord = Integer.parseInt( tfOrginX.getText() );
						int originYCoord = Integer.parseInt( tfOrginY.getText() );
						int width = Integer.parseInt( tfWidth.getText() );
						int height = Integer.parseInt( tfHeight.getText() );
						WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
						
						if((width>0) && (height>0) && (originXCoord >= 0 &&  originXCoord < newWritableImage.getWidth()) && (originYCoord >= 0 &&  originYCoord < newWritableImage.getHeight())) {
							
							if((originXCoord + width) > newWritableImage.getWidth())
								width = (int) (newWritableImage.getWidth() - originXCoord);
							
							if((originYCoord + height) > newWritableImage.getHeight())
								height = (int) (newWritableImage.getHeight() - originYCoord);
							
							PixelReader newPixelReader = newWritableImage.getPixelReader();
							WritableImage newImage = new WritableImage(newPixelReader, originXCoord, originYCoord, width, height);
							imageViewCrop.setFitWidth(newImage.getWidth());
							imageViewCrop.setFitHeight(newImage.getHeight());
							imageViewCrop.setImage(newImage);									
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("OutOfBounds");
							alert.setContentText("Please enter integer coordinates (X & Y) \nwithin bounds. width>0, height>0");
							alert.showAndWait();
						}
					}
					catch(NumberFormatException exception) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("NumberFormatException");
						alert.setContentText("Please enter a valid integer number.");
						alert.showAndWait();
					}
				}
					
					
			});
			
			
			
			btnCrop.setOnAction(ex->{
				if (mainCanvas.getImage() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Empty Image");
					alert.setContentText("There is no image to crop.");
					alert.showAndWait();
				}else {
					try {
						int originXCoord = Integer.parseInt( tfOrginX.getText() );
						int originYCoord = Integer.parseInt( tfOrginY.getText() );
						int width = Integer.parseInt( tfWidth.getText() );
						int height = Integer.parseInt( tfHeight.getText() );
						WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
						
						if((originXCoord > 0 ||  originXCoord < newWritableImage.getWidth()) && (originYCoord > 0 ||  originYCoord < newWritableImage.getHeight())) {
							
							if((originXCoord + width) > newWritableImage.getWidth())
								width = (int) (newWritableImage.getWidth() - originXCoord);
							
							if((originYCoord + height) > newWritableImage.getHeight())
								height = (int) (newWritableImage.getHeight() - originYCoord);
							
							PixelReader newPixelReader = newWritableImage.getPixelReader();
							WritableImage newImage = new WritableImage(newPixelReader, originXCoord, originYCoord, width, height);
							mainCanvas.setImage(newImage);
							menuBar.setDisable(false);
							cropStage.close();							
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("OutOfBounds");
							alert.setContentText("Please enter a integer coordinate within bounds.");
							alert.showAndWait();
						}
					}
					catch(NumberFormatException exception) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("NumberFormatException");
						alert.setContentText("Please enter a valid integer number.");
						alert.showAndWait();
					}
				}
				
			});
			
			btnCancel.setOnAction(ex->{
				menuBar.setDisable(false);
				cropStage.close();
			});
			
			cropStage.setOnCloseRequest(ex->{
				menuBar.setDisable(false);
			});
			
		});
		Menu menuEffects = new Menu("Effects");
		MenuItem InvertColor = new MenuItem("Invert Color");
		InvertColor.setOnAction(e -> {			
			invertColor();			
		});
		
		
		MenuItem SepiaTone = new MenuItem("Sepia Tone");
		
		SepiaTone.setOnAction(e -> {
			if(invertedColor == 1)
				invertColor();
			sepiaTone();  
		});
		
		Menu setTransparent = new Menu("Set Transparent");
		MenuItem transparentBasedOnRGBThreshold = new MenuItem("Based On RGB Threshold");
		MenuItem transparentBasedOnSpecifiedRange = new MenuItem("Based On Specified Range");
		
		
		transparentBasedOnRGBThreshold.setOnAction(e -> {
			menuBar.setDisable(true);
			Pane transparentBasedOnRGBThresholdPane = new Pane();
			
			Scene transparentBasedOnRGBThresholdScene = new Scene(transparentBasedOnRGBThresholdPane, 610, 250);
			Stage transparentBasedOnRGBThresholdStage = new Stage();
			transparentBasedOnRGBThresholdStage.setScene(transparentBasedOnRGBThresholdScene);
			transparentBasedOnRGBThresholdStage.setTitle("Set Transparent Based On RGB Threshold");
			transparentBasedOnRGBThresholdStage.setResizable(false);
			transparentBasedOnRGBThresholdStage.show();
			
			Label lblSelectColour = new Label("Select Colour:");
			lblSelectColour.setLayoutX(10);
			lblSelectColour.setLayoutY(5);
			transparentBasedOnRGBThresholdPane.getChildren().add(lblSelectColour);			
			
			RadioButton rbRed = new RadioButton("Red ");
	        RadioButton rbGreen = new RadioButton("Green ");
	        RadioButton rbBlue = new RadioButton("Blue ");
	        rbRed.setSelected(true);
	        
	        ToggleGroup radioGroupRGB = new ToggleGroup();
	        rbRed.setToggleGroup(radioGroupRGB);
	        rbGreen.setToggleGroup(radioGroupRGB);
	        rbBlue.setToggleGroup(radioGroupRGB);
	        
	        
	        HBox hbox = new HBox(rbRed, rbGreen, rbBlue);
	        hbox.setLayoutX(10);
	        hbox.setLayoutY(25);
	        transparentBasedOnRGBThresholdPane.getChildren().add(hbox);
	        
	        Label lblThreshold = new Label("Select threshold for other colors:");
	        lblThreshold.setLayoutX(10);
	        lblThreshold.setLayoutY(55);	
	        
	        transparentBasedOnRGBThresholdPane.getChildren().add(lblThreshold);
	        
	        Label lblOtherElement = new Label("Green");
	        lblOtherElement.setLayoutX(10);
	        lblOtherElement.setLayoutY(70);	
	        
	        transparentBasedOnRGBThresholdPane.getChildren().add(lblOtherElement);
	        
			Slider sliderOtherElement = new Slider(0.0, 1.0, 0.5);
			sliderOtherElement.setShowTickMarks(true);
			sliderOtherElement.setShowTickLabels(true);
			sliderOtherElement.setMajorTickUnit(0.1f);
			sliderOtherElement.setBlockIncrement(0.05f);
			sliderOtherElement.setLayoutX(10);
			sliderOtherElement.setLayoutY(100);			
			
			transparentBasedOnRGBThresholdPane.getChildren().add(sliderOtherElement);
			
			Label lblOtherElement2 = new Label("Blue");
	        lblOtherElement2.setLayoutX(160);
	        lblOtherElement2.setLayoutY(70);	
	        
	        transparentBasedOnRGBThresholdPane.getChildren().add(lblOtherElement2);
	        
			Slider sliderOtherElement2 = new Slider(0.0, 1.0, 0.5);
			sliderOtherElement2.setShowTickMarks(true);
			sliderOtherElement2.setShowTickLabels(true);
			sliderOtherElement2.setMajorTickUnit(0.1f);
			sliderOtherElement2.setBlockIncrement(0.05f);
			sliderOtherElement2.setLayoutX(160);
			sliderOtherElement2.setLayoutY(100);			
			
			transparentBasedOnRGBThresholdPane.getChildren().add(sliderOtherElement2);
			
			Label labelMessage = new Label("R >=10 and \nG <=0.5 X R and B <=0.5 X R");
			labelMessage.setLayoutX(10);
			labelMessage.setLayoutY(150);
			transparentBasedOnRGBThresholdPane.getChildren().add(labelMessage);
			
			sliderOtherElement.valueProperty().addListener(new ChangeListener<Number>() {
		         public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
		        	 RadioButton selectedRadioButton = (RadioButton) radioGroupRGB.getSelectedToggle();
		        	 if(selectedRadioButton.getText()=="Red ")
		        		 labelMessage.setText( String.format("R >=10 and \nG <=%.2f X R and B <=%.2f X R", sliderOtherElement.getValue(), sliderOtherElement2.getValue()) );
		        	 else if (selectedRadioButton.getText()=="Green ")
		        		 labelMessage.setText( String.format("G >=10 and \nR <=%.2f X G and B <=%.2f X G", sliderOtherElement.getValue(), sliderOtherElement2.getValue()) );
		        	 else
		        		 labelMessage.setText( String.format("B >=10 and \nR <=%.2f X B and G <=%.2f X B", sliderOtherElement.getValue(), sliderOtherElement2.getValue()) );
		          }
		    });
			
			sliderOtherElement2.valueProperty().addListener(new ChangeListener<Number>() {
		         public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
		        	 RadioButton selectedRadioButton = (RadioButton) radioGroupRGB.getSelectedToggle();
		        	 if(selectedRadioButton.getText()=="Red ")
		        		 labelMessage.setText( String.format("R >=10 and \nG <=%.2f X R and B <=%.2f X R", sliderOtherElement.getValue(), sliderOtherElement2.getValue()) );
		        	 else if (selectedRadioButton.getText()=="Green ")
		        		 labelMessage.setText( String.format("G >=10 and \nR <=%.2f X G and B <=%.2f X G", sliderOtherElement.getValue(), sliderOtherElement2.getValue()) );
		        	 else
		        		 labelMessage.setText( String.format("B >=10 and \nR <=%.2f X B and G <=%.2f X B", sliderOtherElement.getValue(), sliderOtherElement2.getValue()) );
		          }
		    });
			
			
			rbRed.setOnAction( ex -> {
				lblOtherElement.setText("Green");
				lblOtherElement2.setText("Blue");
				labelMessage.setText( String.format("R >=10 and \nG <=%.2f X R and B <=%.2f X R", sliderOtherElement.getValue(), sliderOtherElement2.getValue()) );
			});
			rbGreen.setOnAction( ex -> {
				lblOtherElement.setText("Red");
				lblOtherElement2.setText("Blue");
				labelMessage.setText( String.format("G >=10 and \nR <=%.2f X G and B <=%.2f X G", sliderOtherElement.getValue(), sliderOtherElement2.getValue()) );
			});
			rbBlue.setOnAction( ex -> {
				lblOtherElement.setText("Red");
				lblOtherElement2.setText("Green");
				labelMessage.setText( String.format("B >=10 and \nR <=%.2f X B and G <=%.2f X B", sliderOtherElement.getValue(), sliderOtherElement2.getValue()) );
			});
			
					
			Button previewTransparentFit = new Button("Preview (Fit)");
			previewTransparentFit.setLayoutX(10);
			previewTransparentFit.setLayoutY(200);
			
			transparentBasedOnRGBThresholdPane.getChildren().add(previewTransparentFit);
			
			Button previewTransparentNormal = new Button("Preview (Normal)");
			previewTransparentNormal.setLayoutX(160);
			previewTransparentNormal.setLayoutY(200);
			
			transparentBasedOnRGBThresholdPane.getChildren().add(previewTransparentNormal);
			
			Button Cancel = new Button("Cancel");
			Cancel.setLayoutX(380);
			Cancel.setLayoutY(200);
			
			transparentBasedOnRGBThresholdPane.getChildren().add(Cancel);
			
			Button btnSetTransparent = new Button("Set Transparent!");
			btnSetTransparent.setLayoutX(460);
			btnSetTransparent.setLayoutY(200);
			
			transparentBasedOnRGBThresholdPane.getChildren().add(btnSetTransparent);
			
			Cancel.setOnAction(ex -> {
				menuBar.setDisable(false);
				transparentBasedOnRGBThresholdStage.close();
			});
			
			transparentBasedOnRGBThresholdStage.setOnCloseRequest(ex->{
				menuBar.setDisable(false);
			});
			
			ImageView imageViewTransparent = new ImageView();
			ScrollPane transparentCanvasHolder = new ScrollPane();			
			transparentCanvasHolder.setContent(new Group(imageViewTransparent));
			transparentCanvasHolder.setPrefSize(300, 180);
			imageViewTransparent.setCursor(Cursor.CROSSHAIR);
			transparentCanvasHolder.setLayoutX(305);
			transparentCanvasHolder.setLayoutY(10);
			
			transparentBasedOnRGBThresholdPane.getChildren().add(transparentCanvasHolder);
			
			previewTransparentFit.setOnAction(ex->{
				if (mainCanvas.getImage() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Empty Image");
					alert.setContentText("There is no image to preview transparent.");
					alert.showAndWait();
				}else {
					WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
									
					int width = (int)newWritableImage.getWidth();
        			int height = (int)newWritableImage.getHeight();
        			
        			PixelReader newPixelReader = newWritableImage.getPixelReader();
        			PixelWriter newPixelWriter = newWritableImage.getPixelWriter();
        			for(int i = 0; i < height; i++) {
        				for(int j = 0; j < width; j++) {
        					Color c = newPixelReader.getColor(j, i); 
        					Double red = c.getRed() * 255;
        					Double green = c.getGreen() * 255;
        					Double blue = c.getBlue() * 255;
        					       					
        					RadioButton selectedRadioButton = (RadioButton) radioGroupRGB.getSelectedToggle();
	       		        	 if(selectedRadioButton.getText()=="Red ") {
	       		        		if (red >= 10 && (green <= sliderOtherElement.getValue() * red) && (blue <= sliderOtherElement2.getValue() * red)) {
	       		        			Color newColor = new Color(red/255, green/255, blue/255, 0);
	       		        			newPixelWriter.setColor(j, i, newColor);       		        			
	       		        		}
	       		        	 }else if (selectedRadioButton.getText()=="Green ") {
	       		        		if (green >= 10 && (red <= sliderOtherElement.getValue() * green) && (blue <= sliderOtherElement2.getValue() * green)) {
	       		        			Color newColor = new Color(red/255, green/255, blue/255, 0);
	       		        			newPixelWriter.setColor(j, i, newColor);       		        			
	       		        		}       		        	 
	       		        	 }else {
	       		        		if (blue >= 10 && (red <= sliderOtherElement.getValue() * blue) && (green <= sliderOtherElement2.getValue() * blue)) {
	       		        			Color newColor = new Color(red/255, green/255, blue/255, 0);
	       		        			newPixelWriter.setColor(j, i, newColor);       		        			
	       		        		}  
	       		        	}
        					
        				}
           			}
        			
        			imageViewTransparent.setFitWidth(300);
					imageViewTransparent.setFitHeight(180);
					imageViewTransparent.setPreserveRatio(true);
					imageViewTransparent.setImage(newWritableImage);									
				} 
				
			});
			
			previewTransparentNormal.setOnAction(ex->{
				if (mainCanvas.getImage() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Empty Image");
					alert.setContentText("There is no image to preview transparent.");
					alert.showAndWait();
				}else {
					WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
									
					int width = (int)newWritableImage.getWidth();
        			int height = (int)newWritableImage.getHeight();
        			
        			PixelReader newPixelReader = newWritableImage.getPixelReader();
        			PixelWriter newPixelWriter = newWritableImage.getPixelWriter();
        			for(int i = 0; i < height; i++) {
        				for(int j = 0; j < width; j++) {
        					Color c = newPixelReader.getColor(j, i); 
        					Double red = c.getRed() * 255;
        					Double green = c.getGreen() * 255;
        					Double blue = c.getBlue() * 255;
        					       					
        					RadioButton selectedRadioButton = (RadioButton) radioGroupRGB.getSelectedToggle();
	       		        	 if(selectedRadioButton.getText()=="Red ") {
	       		        		if (red >= 10 && (green <= sliderOtherElement.getValue() * red) && (blue <= sliderOtherElement2.getValue() * red)) {
	       		        			Color newColor = new Color(red/255, green/255, blue/255, 0);
	       		        			newPixelWriter.setColor(j, i, newColor);       		        			
	       		        		}
	       		        	 }else if (selectedRadioButton.getText()=="Green ") {
	       		        		if (green >= 10 && (red <= sliderOtherElement.getValue() * green) && (blue <= sliderOtherElement2.getValue() * green)) {
	       		        			Color newColor = new Color(red/255, green/255, blue/255, 0);
	       		        			newPixelWriter.setColor(j, i, newColor);       		        			
	       		        		}       		        	 
	       		        	 }else {
	       		        		if (blue >= 10 && (red <= sliderOtherElement.getValue() * blue) && (green <= sliderOtherElement2.getValue() * blue)) {
	       		        			Color newColor = new Color(red/255, green/255, blue/255, 0);
	       		        			newPixelWriter.setColor(j, i, newColor);       		        			
	       		        		}  
	       		        	}
        					
        				}
           			}
        		
        			
					imageViewTransparent.setFitWidth(newWritableImage.getWidth());
					imageViewTransparent.setFitHeight(newWritableImage.getHeight());
					imageViewTransparent.setPreserveRatio(true);
					imageViewTransparent.setImage(newWritableImage);								
				} 
				
			});
			
			btnSetTransparent.setOnAction(ex-> {
						
				if (mainCanvas.getImage() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Empty Image");
					alert.setContentText("There is no image to set transparent.");
					alert.showAndWait();
				}else {
					WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
									
					int width = (int)newWritableImage.getWidth();
        			int height = (int)newWritableImage.getHeight();
        			
        			PixelReader newPixelReader = newWritableImage.getPixelReader();
        			PixelWriter newPixelWriter = newWritableImage.getPixelWriter();
        			for(int i = 0; i < height; i++) {
        				for(int j = 0; j < width; j++) {
        					Color c = newPixelReader.getColor(j, i); 
        					Double red = c.getRed() * 255;
        					Double green = c.getGreen() * 255;
        					Double blue = c.getBlue() * 255;
        					       					
        					RadioButton selectedRadioButton = (RadioButton) radioGroupRGB.getSelectedToggle();
	       		        	 if(selectedRadioButton.getText()=="Red ") {
	       		        		if (red >= 10 && (green <= sliderOtherElement.getValue() * red) && (blue <= sliderOtherElement2.getValue() * red)) {
	       		        			Color newColor = new Color(red/255, green/255, blue/255, 0);
	       		        			newPixelWriter.setColor(j, i, newColor);       		        			
	       		        		}
	       		        	 }else if (selectedRadioButton.getText()=="Green ") {
	       		        		if (green >= 10 && (red <= sliderOtherElement.getValue() * green) && (blue <= sliderOtherElement2.getValue() * green)) {
	       		        			Color newColor = new Color(red/255, green/255, blue/255, 0);
	       		        			newPixelWriter.setColor(j, i, newColor);       		        			
	       		        		}       		        	 
	       		        	 }else {
	       		        		if (blue >= 10 && (red <= sliderOtherElement.getValue() * blue) && (green <= sliderOtherElement2.getValue() * blue)) {
	       		        			Color newColor = new Color(red/255, green/255, blue/255, 0);
	       		        			newPixelWriter.setColor(j, i, newColor);       		        			
	       		        		}  
	       		        	}
        					
        				}
           			}
        			mainCanvas.setImage(newWritableImage);	
        			menuBar.setDisable(false);
    				transparentBasedOnRGBThresholdStage.close();
				}
				
			});
			
			
		});
		
		transparentBasedOnSpecifiedRange.setOnAction(e -> {
			Pane transparentBasedOnSpecifiedRangePane = new Pane();
			
			Scene transparentBasedOnSpecifiedRangeScene = new Scene(transparentBasedOnSpecifiedRangePane, 600, 250);
			Stage transparentBasedOnSpecifiedRangeStage = new Stage();
			transparentBasedOnSpecifiedRangeStage.setScene(transparentBasedOnSpecifiedRangeScene);
			transparentBasedOnSpecifiedRangeStage.setTitle("Set Transparent Based On Specified RGB Range");
			transparentBasedOnSpecifiedRangeStage.setResizable(false);
			transparentBasedOnSpecifiedRangeStage.show();
			
			Label lblRed = new Label("Red between: \n0-255                              &");
			lblRed.setLayoutX(10);
			lblRed.setLayoutY(5);
			transparentBasedOnSpecifiedRangePane.getChildren().add(lblRed);	
			
			Label lblGreen = new Label("Green between: \n0-255                              &");
			lblGreen.setLayoutX(10);
			lblGreen.setLayoutY(55);
			transparentBasedOnSpecifiedRangePane.getChildren().add(lblGreen);	
			
			Label lblBlue = new Label("Blue between: \n0-255                              &");
			lblBlue.setLayoutX(10);
			lblBlue.setLayoutY(105);
			transparentBasedOnSpecifiedRangePane.getChildren().add(lblBlue);
			
			TextField tfRedFirst = new TextField();
			tfRedFirst.setLayoutX(120);
			tfRedFirst.setLayoutY(5);
			tfRedFirst.setMaxWidth(50);
			transparentBasedOnSpecifiedRangePane.getChildren().add(tfRedFirst);
			
			TextField tfRedLast = new TextField();
			tfRedLast.setLayoutX(190);
			tfRedLast.setLayoutY(5);
			tfRedLast.setMaxWidth(50);
			transparentBasedOnSpecifiedRangePane.getChildren().add(tfRedLast);
			
			TextField tfGreenFirst = new TextField();
			tfGreenFirst.setLayoutX(120);
			tfGreenFirst.setLayoutY(55);
			tfGreenFirst.setMaxWidth(50);
			transparentBasedOnSpecifiedRangePane.getChildren().add(tfGreenFirst);
			
			TextField tfGreenLast = new TextField();
			tfGreenLast.setLayoutX(190);
			tfGreenLast.setLayoutY(55);
			tfGreenLast.setMaxWidth(50);
			transparentBasedOnSpecifiedRangePane.getChildren().add(tfGreenLast);
			
			TextField tfBlueFirst = new TextField();
			tfBlueFirst.setLayoutX(120);
			tfBlueFirst.setLayoutY(105);
			tfBlueFirst.setMaxWidth(50);
			transparentBasedOnSpecifiedRangePane.getChildren().add(tfBlueFirst);
			
			TextField tfBlueLast = new TextField();
			tfBlueLast.setLayoutX(190);
			tfBlueLast.setLayoutY(105);
			tfBlueLast.setMaxWidth(50);
			transparentBasedOnSpecifiedRangePane.getChildren().add(tfBlueLast);
			
			Button previewTransparentFit = new Button("Preview (Fit)");
			previewTransparentFit.setLayoutX(10);
			previewTransparentFit.setLayoutY(200);
			
			transparentBasedOnSpecifiedRangePane.getChildren().add(previewTransparentFit);
			
			Button previewTransparentNormal = new Button("Preview (Normal)");
			previewTransparentNormal.setLayoutX(160);
			previewTransparentNormal.setLayoutY(200);
			
			transparentBasedOnSpecifiedRangePane.getChildren().add(previewTransparentNormal);
			
			Button Cancel = new Button("Cancel");
			Cancel.setLayoutX(380);
			Cancel.setLayoutY(200);
			
			transparentBasedOnSpecifiedRangePane.getChildren().add(Cancel);
			
			Button btnSetTransparent = new Button("Set Transparent!");
			btnSetTransparent.setLayoutX(460);
			btnSetTransparent.setLayoutY(200);
			
			transparentBasedOnSpecifiedRangePane.getChildren().add(btnSetTransparent);
			
			Cancel.setOnAction(ex -> {
				menuBar.setDisable(false);
				transparentBasedOnSpecifiedRangeStage.close();
			});
			
			transparentBasedOnSpecifiedRangeStage.setOnCloseRequest(ex->{
				menuBar.setDisable(false);
			});
			
			ImageView imageViewTransparent = new ImageView();
			ScrollPane transparentCanvasHolder = new ScrollPane();			
			transparentCanvasHolder.setContent(new Group(imageViewTransparent));
			transparentCanvasHolder.setPrefSize(300, 180);
			imageViewTransparent.setCursor(Cursor.CROSSHAIR);
			transparentCanvasHolder.setLayoutX(290);
			transparentCanvasHolder.setLayoutY(10);
			
			transparentBasedOnSpecifiedRangePane.getChildren().add(transparentCanvasHolder);
			
			previewTransparentFit.setOnAction(ex->{
				
				try {
					int redFirst = Integer.parseInt(tfRedFirst.getText());
					int redLast = Integer.parseInt(tfRedLast.getText());
					int greenFirst = Integer.parseInt(tfGreenFirst.getText());
					int greenLast = Integer.parseInt(tfGreenLast.getText());
					int blueFirst = Integer.parseInt(tfBlueFirst.getText()); 
					int blueLast = Integer.parseInt(tfBlueLast.getText());				
				
					if (mainCanvas.getImage() == null) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Empty Image");
						alert.setContentText("There is no image to preview transparent.");
						alert.showAndWait();
					}else if( (redFirst >= 0) && (redLast <= 255) && (greenFirst >= 0) && (greenLast <= 255) && (blueFirst >= 0) && (blueLast <= 255) &&
							  (redFirst <= redLast) && (greenFirst <= greenLast) && (blueFirst <= blueLast) )
					{
						WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
										
						int width = (int)newWritableImage.getWidth();
	        			int height = (int)newWritableImage.getHeight();
	        			
	        			PixelReader newPixelReader = newWritableImage.getPixelReader();
	        			PixelWriter newPixelWriter = newWritableImage.getPixelWriter();
	        			for(int i = 0; i < height; i++) {
	        				for(int j = 0; j < width; j++) {
	        					Color c = newPixelReader.getColor(j, i); 
	        					Double red = c.getRed() * 255;
	        					Double green = c.getGreen() * 255;
	        					Double blue = c.getBlue() * 255;
	        					
	        					if( (red >= redFirst && red <= redLast) && (green >= greenFirst && green <= greenLast) && (blue >= blueFirst && blue <= blueLast) ) {
	        						
	        						Color newColor = new Color(red/255, green/255, blue/255, 0);
	        						newPixelWriter.setColor(j, i, newColor);      
	        						
	        					}
	        					
	        				}
	           			}
	        			
	        			imageViewTransparent.setFitWidth(300);
						imageViewTransparent.setFitHeight(180);
						imageViewTransparent.setPreserveRatio(true);
						imageViewTransparent.setImage(newWritableImage);	
						
					}else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Invalid Range");
						alert.setContentText("Make sure the range is between 0 and 255 inclusive.");
						alert.showAndWait();					
					}
				
				}
				catch(NumberFormatException exception) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("NumberFormatException");
					alert.setContentText("Please enter a valid integer number between 0 and 255 inclusive.");
					alert.showAndWait();
				}
				
			});
			
			previewTransparentNormal.setOnAction(ex->{
				try {
					int redFirst = Integer.parseInt(tfRedFirst.getText());
					int redLast = Integer.parseInt(tfRedLast.getText());
					int greenFirst = Integer.parseInt(tfGreenFirst.getText());
					int greenLast = Integer.parseInt(tfGreenLast.getText());
					int blueFirst = Integer.parseInt(tfBlueFirst.getText()); 
					int blueLast = Integer.parseInt(tfBlueLast.getText());				
				
					if (mainCanvas.getImage() == null) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Empty Image");
						alert.setContentText("There is no image to preview transparent.");
						alert.showAndWait();
					}else if( (redFirst >= 0) && (redLast <= 255) && (greenFirst >= 0) && (greenLast <= 255) && (blueFirst >= 0) && (blueLast <= 255) &&
							  (redFirst <= redLast) && (greenFirst <= greenLast) && (blueFirst <= blueLast) )
					{
						WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
										
						int width = (int)newWritableImage.getWidth();
	        			int height = (int)newWritableImage.getHeight();
	        			
	        			PixelReader newPixelReader = newWritableImage.getPixelReader();
	        			PixelWriter newPixelWriter = newWritableImage.getPixelWriter();
	        			for(int i = 0; i < height; i++) {
	        				for(int j = 0; j < width; j++) {
	        					Color c = newPixelReader.getColor(j, i); 
	        					Double red = c.getRed() * 255;
	        					Double green = c.getGreen() * 255;
	        					Double blue = c.getBlue() * 255;
	        					
	        					if( (red >= redFirst && red <= redLast) && (green >= greenFirst && green <= greenLast) && (blue >= blueFirst && blue <= blueLast) ) {
	        						
	        						Color newColor = new Color(red/255, green/255, blue/255, 0);
	        						newPixelWriter.setColor(j, i, newColor);      
	        						
	        					}
	        					
	        				}
	           			}
	        			
	        			imageViewTransparent.setFitWidth(newWritableImage.getWidth());
						imageViewTransparent.setFitHeight(newWritableImage.getHeight());
						imageViewTransparent.setPreserveRatio(true);
						imageViewTransparent.setImage(newWritableImage);	
						
					}else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Invalid Range");
						alert.setContentText("Make sure the range is between 0 and 255 inclusive.");
						alert.showAndWait();					
					}
				
				}
				catch(NumberFormatException exception) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("NumberFormatException");
					alert.setContentText("Please enter a valid integer number between 0 and 255 inclusive.");
					alert.showAndWait();
				}
			});
			
			btnSetTransparent.setOnAction(ex-> {
				
				try {
					int redFirst = Integer.parseInt(tfRedFirst.getText());
					int redLast = Integer.parseInt(tfRedLast.getText());
					int greenFirst = Integer.parseInt(tfGreenFirst.getText());
					int greenLast = Integer.parseInt(tfGreenLast.getText());
					int blueFirst = Integer.parseInt(tfBlueFirst.getText()); 
					int blueLast = Integer.parseInt(tfBlueLast.getText());				
				
					if (mainCanvas.getImage() == null) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Empty Image");
						alert.setContentText("There is no image to preview transparent.");
						alert.showAndWait();
					}else if( (redFirst >= 0) && (redLast <= 255) && (greenFirst >= 0) && (greenLast <= 255) && (blueFirst >= 0) && (blueLast <= 255) &&
							  (redFirst <= redLast) && (greenFirst <= greenLast) && (blueFirst <= blueLast) )
					{
						WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
										
						int width = (int)newWritableImage.getWidth();
	        			int height = (int)newWritableImage.getHeight();
	        			
	        			PixelReader newPixelReader = newWritableImage.getPixelReader();
	        			PixelWriter newPixelWriter = newWritableImage.getPixelWriter();
	        			for(int i = 0; i < height; i++) {
	        				for(int j = 0; j < width; j++) {
	        					Color c = newPixelReader.getColor(j, i); 
	        					Double red = c.getRed() * 255;
	        					Double green = c.getGreen() * 255;
	        					Double blue = c.getBlue() * 255;
	        					
	        					if( (red >= redFirst && red <= redLast) && (green >= greenFirst && green <= greenLast) && (blue >= blueFirst && blue <= blueLast) ) {
	        						
	        						Color newColor = new Color(red/255, green/255, blue/255, 0);
	        						newPixelWriter.setColor(j, i, newColor);      
	        						
	        					}
	        					
	        				}
	           			}
	        			
	        			mainCanvas.setImage(newWritableImage);	
	        			menuBar.setDisable(false);
	        			transparentBasedOnSpecifiedRangeStage.close();
						
					}else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Invalid Range");
						alert.setContentText("Make sure the range is between 0 and 255 inclusive.");
						alert.showAndWait();					
					}
				
				}
				catch(NumberFormatException exception) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("NumberFormatException");
					alert.setContentText("Please enter a valid integer number between 0 and 255 inclusive.");
					alert.showAndWait();
				}
				
			});
			
		});
				
		setTransparent.getItems().addAll(transparentBasedOnRGBThreshold, transparentBasedOnSpecifiedRange);
		
		menuEffects.getItems().addAll(InvertColor, SepiaTone);
		menuEdit.getItems().addAll(rotateRight, rotateLeft, crop, menuEffects, setTransparent);
		
		Menu menuView = new Menu("View");
		MenuItem zoomIn = new MenuItem("Zoom In");
		zoomIn.setOnAction(e -> {
			String zoomPercentage = String.format("%.0f", mainCanvas.getScaleX() * 100);
			if(!zoomPercentage.equals("500")) {
				mainCanvas.setScaleX(mainCanvas.getScaleX() + 0.1 );
				mainCanvas.setScaleY(mainCanvas.getScaleY() + 0.1 );
				zoomPercentage = String.format("%.0f", mainCanvas.getScaleX() * 100);
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Zoom Error");
				alert.setHeaderText("Maximum Zoom In reached");
				alert.setContentText("5 X Zoom In (500% of Original Image) reached");
				alert.showAndWait();
			}
			lblZoomPercent.setText("Zoom Percent: " + zoomPercentage + "%");
		});
		MenuItem zoomOut = new MenuItem("Zoom Out");
		zoomOut.setOnAction(e -> {
			String zoomPercentage = String.format("%.0f", mainCanvas.getScaleX() * 100);
			if(!zoomPercentage.equals("20")) {
				mainCanvas.setScaleX(mainCanvas.getScaleX() - 0.1 );
				mainCanvas.setScaleY(mainCanvas.getScaleY() - 0.1 );
				zoomPercentage = String.format("%.0f", mainCanvas.getScaleX() * 100);
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Zoom Error");
				alert.setHeaderText("Maximum Zoom Out reached");
				alert.setContentText("5 X Zoom Out (20% of Original Image) reached");
				alert.showAndWait();
			}
			lblZoomPercent.setText("Zoom Percent: " + zoomPercentage + "%");
		});
		MenuItem maxZoomIn = new MenuItem("Max Zoom In");
		maxZoomIn.setOnAction(e -> {
			mainCanvas.setScaleX(5.0);
			mainCanvas.setScaleY(5.0);
			String zoomPercentage = String.format("%.0f", mainCanvas.getScaleX() * 100);
			lblZoomPercent.setText("Zoom Percent: " + zoomPercentage + "%");
		});
		MenuItem maxZoomOut = new MenuItem("Max Zoom Out");
		maxZoomOut.setOnAction(e -> {
			mainCanvas.setScaleX(0.2);
			mainCanvas.setScaleY(0.2);
			String zoomPercentage = String.format("%.0f", mainCanvas.getScaleX() * 100);
			lblZoomPercent.setText("Zoom Percent: " + zoomPercentage + "%");
		});
		MenuItem resetZoom = new MenuItem("Reset Zoom");
		resetZoom.setOnAction(e -> {
			mainCanvas.setScaleX(1);
			mainCanvas.setScaleY(1);
			String zoomPercentage = String.format("%.0f", mainCanvas.getScaleX() * 100);
			lblZoomPercent.setText("Zoom Percent: " + zoomPercentage + "%");
		});
		menuView.getItems().addAll(zoomIn, zoomOut, maxZoomIn, maxZoomOut, resetZoom);
						
		Menu menuHelp = new Menu("Help");
		MenuItem about = new MenuItem("About Ultra Image-Viewer");
		about.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About Ultra Picture-Viewer");
			alert.setHeaderText("Ultra Picure-Viewer");
			alert.setContentText("Ultra Picture-Viewer is an image utility tool.\nThis is Ultra Picture-Viewer version 1.94.\nCreated by Vision Paudel.");
			alert.showAndWait();
		});
		menuHelp.getItems().addAll(about);
			
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuHelp);						
		mainPane.setTop(menuBar);
		
		colorValue.setLayoutX(0);
		colorValue.setLayoutY(480);
		colorPane.getChildren().add(colorValue);
		
		pixelColor.setLayoutX(0);
		pixelColor.setLayoutY(500);
		pixelColor.setStroke(Color.BLACK);
		colorPane.getChildren().add(pixelColor);
		
		labelCoordinates.setLayoutX(5);
		labelCoordinates.setLayoutY(560);
		colorPane.getChildren().add(labelCoordinates);
		mainCanvas.setOnMouseMoved(this::handleMove);
        				
		Scene mainScene = new Scene(mainPane, 1200, 720);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Ultra Picture-Viewer");
		primaryStage.setResizable(false);
		primaryStage.show();
	}		
	
	private void sepiaTone() {
		if (sepia == 0) {
			SepiaTone sepiaTone = new SepiaTone(); 
		    sepiaTone.setLevel(0.8); 
		    mainCanvas.setEffect(sepiaTone); 
		    sepia = 1;
		}else {
			SepiaTone sepiaTone = new SepiaTone(); 
		    sepiaTone.setLevel(0); 
		    mainCanvas.setEffect(sepiaTone); 
		    sepia = 0;
		}		
	}

	private void invertColor() {
		if (sepia == 1)
			sepiaTone();
		WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
		PixelReader newPixelReader = newWritableImage.getPixelReader();
		PixelWriter newPixelWriter = newWritableImage.getPixelWriter();
		int width = (int)newWritableImage.getWidth();
		int height = (int)newWritableImage.getHeight();
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				newPixelWriter.setColor(j, i, newPixelReader.getColor(j, i).invert());					
			}				
		}
		if(invertedColor == 1)
			invertedColor = 0;
		else
			invertedColor = 1;
		mainCanvas.setImage(newWritableImage);		
	}

	private void handleMove(MouseEvent e) {
		
		if(mainCanvas.getScaleX()==1 && mainCanvas.getRotate()==0) {		
			WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
			PixelReader newPixelReader = newWritableImage.getPixelReader();
			Color colorAtCoordinate = newPixelReader.getColor((int)e.getX(), (int)e.getY());
			
			String xCoordinate = String.format("%.1f", e.getX());
			String yCoordinate = String.format("%.1f", e.getY());
			labelCoordinates.setText(xCoordinate + ", " + yCoordinate + " px");
			colorValue.setText("Color: " + colorAtCoordinate);
			pixelColor.setFill(colorAtCoordinate);
		}
		else {
			String xCoordinate = String.format("%.1f", e.getX());
			String yCoordinate = String.format("%.1f", e.getY());
			labelCoordinates.setText(xCoordinate + ", " + yCoordinate + " px");
			colorValue.setText("Color: undefined");
			pixelColor.setFill(Color.BLACK);
		}
		
    }
	
}
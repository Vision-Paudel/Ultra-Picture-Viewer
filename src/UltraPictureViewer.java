
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
	Label colorValue = new Label("Color: ");
	
	Label labelCoordinates = new Label("0, 0 px");
	static int sepia = 0;
		
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
	        	 colorAdjust.setBrightness((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	        	 String brightnessValue = String.format("%.3f", (double)newValue);
	        	 lblBrightness.setText("Brightness: " + brightnessValue);
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
	        	 colorAdjust.setContrast((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	        	 String contrastValue = String.format("%.3f", (double)newValue);
	        	 lblContrast.setText("Contrast: " + contrastValue);
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
	        	 colorAdjust.setHue((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	        	 String hueValue = String.format("%.3f", (double)newValue);
	        	 lblHue.setText("Hue: " + hueValue);
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
	        	 colorAdjust.setSaturation((double)newValue);
	        	 mainCanvas.setEffect(colorAdjust);
	        	 String saturationValue = String.format("%.3f", (double)newValue);
	        	 lblSaturation.setText("Saturation: " + saturationValue);
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
	        					if(j < width -1) {
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
		MenuItem crop = new MenuItem("Crop");
		crop.setOnAction(e -> {
			menuBar.setDisable(true);
			
			Pane cropPane = new Pane();
			
			Scene cropScene = new Scene(cropPane, 400, 250);
			Stage cropStage = new Stage();
			cropStage.setScene(cropScene);
			cropStage.setTitle("Crop Settings");
			cropStage.setResizable(false);
			cropStage.show();
			
			Label originX = new Label("Origin x-coordinate: ");
			originX.setLayoutX(25);
			originX.setLayoutY(20);
			cropPane.getChildren().add(originX);
			
			TextField tfOrginX = new TextField();
			tfOrginX.setLayoutX(180);
			tfOrginX.setLayoutY(20);
			cropPane.getChildren().add(tfOrginX);
			
			Label originY = new Label("Origin y-coordinate: ");
			originY.setLayoutX(25);
			originY.setLayoutY(60);
			cropPane.getChildren().add(originY);
			
			TextField tfOrginY = new TextField();
			tfOrginY.setLayoutX(180);
			tfOrginY.setLayoutY(60);
			cropPane.getChildren().add(tfOrginY);
			
			Label cropWidth = new Label("Crop Width: ");
			cropWidth.setLayoutX(25);
			cropWidth.setLayoutY(100);
			cropPane.getChildren().add(cropWidth);
			
			TextField tfWidth = new TextField();
			tfWidth.setLayoutX(180);
			tfWidth.setLayoutY(100);
			cropPane.getChildren().add(tfWidth);
			
			Label cropHeight = new Label("Crop Height: ");
			cropHeight.setLayoutX(25);
			cropHeight.setLayoutY(140);
			cropPane.getChildren().add(cropHeight);
			
			TextField tfHeight = new TextField();
			tfHeight.setLayoutX(180);
			tfHeight.setLayoutY(140);
			cropPane.getChildren().add(tfHeight);
			
			Button btnCrop = new Button("Crop");
			btnCrop.setLayoutX(320);
			btnCrop.setLayoutY(200);
			cropPane.getChildren().add(btnCrop);
			
			Button btnCancel = new Button("Cancel");
			btnCancel.setLayoutX(250);
			btnCancel.setLayoutY(200);
			cropPane.getChildren().add(btnCancel);
			
			btnCrop.setOnAction(ex->{
				if (mainCanvas.getImage() == null) {
					menuBar.setDisable(false);
					cropStage.close();	
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
			mainCanvas.setImage(newWritableImage);
		});
		
		
		MenuItem SepiaTone = new MenuItem("Sepia Tone");
		
		SepiaTone.setOnAction(e -> {
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
		});
		menuEffects.getItems().addAll(InvertColor, SepiaTone);
		menuEdit.getItems().addAll(crop, menuEffects);
		
		Menu menuView = new Menu("View");
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
			alert.setContentText("Ultra Picture-Viewer is an image utility tool.\nThis is Ultra Picture-Viewer version 1.9.\nCreated by Vision Paudel.");
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
	
	private void handleMove(MouseEvent e) {
		
		WritableImage newWritableImage = mainCanvas.snapshot(new SnapshotParameters(), null);
		PixelReader newPixelReader = newWritableImage.getPixelReader();
		Color colorAtCoordinate = newPixelReader.getColor((int)e.getX(), (int)e.getY());
		
		String xCoordinate = String.format("%.1f", e.getX());
		String yCoordinate = String.format("%.1f", e.getY());
		labelCoordinates.setText(xCoordinate + ", " + yCoordinate + " px");
		colorValue.setText("Color: " + colorAtCoordinate);
		pixelColor.setFill(colorAtCoordinate);
    }
	
}
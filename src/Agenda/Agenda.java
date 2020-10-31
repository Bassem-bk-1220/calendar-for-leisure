/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Agenda extends Application {
    Statement statement;
    
    
    Text msg = new Text("");
    Text ut = new Text("name");
    Text pt = new Text("adress");
    Text pc = new Text("city"); 
    Text pd = new Text("date"); 
    Text vu = new Text("");
     Text vp = new Text("");
    String city_ofroumania[] ={"Bucarest","Lasi","Arad","Timisoira","Cluj-napco"};
     ComboBox combo_box =new ComboBox(FXCollections.observableArrayList(city_ofroumania));
      TilePane tp = new TilePane(combo_box);
      DatePicker d = new DatePicker();
     TextField uf = new TextField();
     TextField pf = new TextField();
     TextField cf = new TextField();
     TextField df = new TextField();
      Label label = new Label("no files selected");
    Button button = new Button("open l'image "); 
    Button bl = new Button("add");
    Button bu = new Button("Update");
    Button bd = new Button("Delete");
    @Override
    public void start(Stage primaryStage) {
         
    
         DBConnect();
        
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER); 
        //space between rows / columns
        root.setHgap(10);
        root.setVgap(10); 
        //space around edges of grid pane
        root.setPadding(new Insets(25, 25, 25, 25)); //(top, right, bottom, left)
Text scenetitle = new Text("Agenda pour les loisirs");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        root.add(scenetitle, 0, 0, 2, 1);
        
         BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));

         root.setStyle("-fx-background-color:  linear-gradient(lightgray, gray);" +"fx-border-color: white;"+"-fx-border-radius: 20;"+"-fx-padding: 10 10 10 10;"+"-fx-background-radius: 20;");
bp.setStyle("-fx-background-color:  linear-gradient(gray,DimGrey );");
scenetitle.setStyle("-fx-fill:  linear-gradient(orange , orangered);");
 bl.setStyle("-fx-background-radius: 30, 30, 29, 28;\n" +"    -fx-padding: 3px 10px 3px 10px;\n" +"    -fx-background-color: linear-gradient(orange, orangered );");
 bu.setStyle("-fx-background-radius: 30, 30, 29, 28;\n" +"    -fx-padding: 3px 10px 3px 10px;\n" +"    -fx-background-color: linear-gradient(orange, orangered );");
 bd.setStyle("-fx-background-radius: 30, 30, 29, 28;\n" +"    -fx-padding: 3px 10px 3px 10px;\n" +"    -fx-background-color: linear-gradient(orange, orangered );");
 button.setStyle("-fx-background-radius: 30, 30, 29, 28;\n" +"    -fx-padding: 3px 10px 3px 10px;\n" +"    -fx-background-color: linear-gradient(orange, orangered );");
                
        root.add(ut, 0, 1);
        root.add(uf, 1, 1);
        root.add(pt, 0, 2);
        root.add(pf, 1, 2);
        root.add(pc, 0, 3);
        root.add(cf, 1, 3);
        root.add(pd, 0, 4);
        root.add(df, 1, 4);
        root.add(bl, 0,6);
        root.add(bu, 1,6);
        root.add(bd, 2,6);
    root.add(msg, 0,7);
    
        FileChooser fil_chooser = new FileChooser();
        Label Image = new Label("Image:");
        root.add(Image, 0, 5);
         root.add(label, 1, 5);
        root.add(button, 2, 5);
        EventHandler<ActionEvent> event;
        event = new EventHandler<ActionEvent>() { 
            
            public void handle(ActionEvent e)
            {
                
                // get the file selected
                File file = fil_chooser.showOpenDialog(primaryStage);
                
                if (file != null) {
                    
                    label.setText(file.getAbsolutePath());
                   
                } 
            }
            
        }; 
        button.setOnAction(event);    
       
        bl.setOnAction(e->insert());
        bu.setOnAction(e->update());
        bd.setOnAction(e->delete());
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("java");
        primaryStage.show();

            
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void DBConnect(){
        try {
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost/java","root","mysql");
            statement = conn.createStatement();
            msg.setText("Database Connected");
        } catch (Exception e) {
            msg.setText("Database Not Connected");
        }
    }
    
    public void insert(){
        try{
            String insertquery = "INSERT INTO `Event`(`name`, `adress`,`city`,`date`,`image`) VALUES ('"+uf.getText()+"', '"+pf.getText()+"','"+cf.getText()+"','"+df.getText()+"','"+label.getText()+"')";
            statement.executeUpdate(insertquery);
            ResultSet rs=statement.executeQuery("select * from Event");
            while(rs.next()){
System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4)+"|"+rs.getString(5));
}
            msg.setText("Insered");
        } catch(Exception e){
           msg.setText(" Not inserd");
        }
    }
     
     
     public void update(){
         int id = 1;
        try {
            String insertquery = "UPDATE `Event` set `name`='"+uf.getText()+"',`adress`='"+pf.getText()+"',`city`='"+cf.getText()+"',`date`='"+df.getText()+"',`image`='"+label.getText()+"' WHERE id = '"+id+"'";
            statement.executeUpdate(insertquery);
            ResultSet rs=statement.executeQuery("select * from Event");
            while(rs.next()){
System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4)+"|"+rs.getString(5));
}
            msg.setText("Updated");
        } catch (SQLException ex) {
            msg.setText(ex.getMessage());
        }
     }
     
    /**
     *
     */
    public void delete(){
        int id = 3;
         try {
            String insertquery = "DELETE FROM `Event` WHERE id = '"+id+"'";
            statement.executeUpdate(insertquery);
              ResultSet rs=statement.executeQuery("select * from Event");
            while(rs.next()){
System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4)+"|"+rs.getString(5));
}
            msg.setText("Deleted");
        } catch (SQLException ex) {
            msg.setText(ex.getMessage());
        }
     }

}

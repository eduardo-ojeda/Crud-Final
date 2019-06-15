package application;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import application.CRUDCli;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {

    private TextField txfNombre = new TextField();
    private TextField txfApellido = new TextField();
    private TextField txfDireccion = new TextField();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private TabPane tabpane = new TabPane();
    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane border = new BorderPane();
        primaryStage.setTitle("Boletaje");

        TabPane tabpane = addPanel();
        border.setTop(tabpane);

        primaryStage.setScene(new Scene(border, 600, 500));
        primaryStage.show();

    }
    //INSERTAR
    public GridPane addGrindPane (){
        GridPane grid = new GridPane();
        Label lNombre = new Label("Nombre");
        Label lApellido = new Label("Apellido");
        Label lDireccion = new Label("Direccion");
        Button btnEnviar = new Button("Agregar");

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,10,0,10));

        Text Unidades = new Text("Insertar");
        Unidades.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(Unidades,3,1);

        grid.add(lNombre, 3,2);
        grid.add(lApellido, 3,3);
        grid.add(lDireccion, 3,4);

        txfNombre.setPromptText("Nombre");
        GridPane.setConstraints(txfNombre, 4, 2);
        grid.getChildren().add(txfNombre);

        txfApellido.setPromptText("Apellido");
        GridPane.setConstraints(txfApellido, 4, 3);
        grid.getChildren().add(txfApellido);

        txfDireccion.setPromptText("Direccion");
        GridPane.setConstraints(txfDireccion, 4, 4);
        grid.getChildren().add(txfDireccion);

        btnEnviar.setPrefSize(150,20);
        btnEnviar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }



                    CRUDCli opCliente = new CRUDCli(accesoBD.getConnection());
                    opCliente.insertCliente(txfNombre.getText(), txfApellido.getText(), txfDireccion.getText());
                    alert.setTitle("Registro");
                    alert.setHeaderText("Registro Exitoso");
                    alert.showAndWait();

            }
        });

        grid.add(btnEnviar,4,5);
        return grid;
    }

    //BUSCAR
    public GridPane addGrindPane2 (){
        GridPane grid = new GridPane();
        TableView tabla = new TableView();
        TableColumn idNombre = new TableColumn("clienteId");
        idNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("clienteId"));
        TableColumn colNombre = new TableColumn("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("Nombre"));
        TableColumn colApellido = new TableColumn ("apellidos");
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidos"));
        TableColumn colDireccion = new TableColumn ("Direccion");
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente,String>("direccion"));

        tabla.getColumns().addAll(idNombre,colNombre,colApellido,colDireccion);

        Label lBuscar = new Label("BUSCAR");
        TextField txfID = new TextField();
        Button btnBuscar = new Button("Buscar");

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,10,0,10));



        txfID.setPromptText("Ingrese Nombre");
        GridPane.setConstraints(txfID, 1, 0);

        grid.getChildren().add(txfID);
        grid.getChildren().add(tabla);

        btnBuscar.setPrefSize(150,20);
        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String nombre1 = txfID.getText();
                CRUDCli opCliente = new CRUDCli(accesoBD.getConnection());
                ArrayList<Cliente> arrayClientes;
                arrayClientes = opCliente.getClientes(nombre1);

                ObservableList<Cliente> data = FXCollections.observableArrayList();
                data.removeAll();
                data.addAll(arrayClientes);
                tabla.setItems(data);
                txfID.setText("");

                if(arrayClientes.size()==0){
                    alert.setTitle("No encontrado");
                    alert.setHeaderText("NO SE ENCONTRO NADA");
                    alert.showAndWait();
                }else{
                    alert.setTitle("Buscar");
                    alert.setHeaderText("Busqueda Exitosa");
                    alert.showAndWait();
                }

            }
        });

        grid.add(btnBuscar,2,0);

        return grid;
    }

    //ACTUALIZAR
    private GridPane addGrindPane3() {

        GridPane grid = new GridPane();
        TextField txfNombre = new TextField();
        TextField txfNombreA = new TextField();
        TextField txfApellido = new TextField();
        TextField txfDireccion = new TextField();
        Button btnBuscar = new Button ("Buscar");
        Button btnActualizar = new Button("Actualizar");
        Label Buscar = new Label ("Ingrese ID");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,10,0,10));

        Text Unidades= new Text("Actualizar");
        Unidades.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(Unidades, 2,1);
        grid.add(Buscar, 2,2);

        txfNombre.setPromptText("ID");
        GridPane.setConstraints(txfNombre, 3, 2);
        grid.getChildren().add(txfNombre);

        txfNombreA.setPromptText("Nombre");
        GridPane.setConstraints(txfNombreA, 1, 4);
        grid.getChildren().add(txfNombreA);

        txfApellido.setPromptText("Apellido");
        GridPane.setConstraints(txfApellido, 2, 4);
        grid.getChildren().add(txfApellido);

        txfDireccion.setPromptText("Direccion");
        GridPane.setConstraints(txfDireccion, 3, 4);
        grid.getChildren().add(txfDireccion);

        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String nombre2 = txfNombre.getText();
                int id1 = Integer.parseInt(nombre2);
                CRUDCli opCliente = new CRUDCli(accesoBD.getConnection());
                Cliente clt;
                clt = opCliente.getCliente(id1);
                System.out.print(clt.getNombre());

                if(clt.getNombre().equals(""))
                {
                    alert.setTitle("No encontrado");
                    alert.setHeaderText("No existe registro");
                    alert.showAndWait();
                    txfNombreA.setText("");
                    txfApellido.setText("");
                    txfDireccion.setText("");
                }
                else{
                    alert.setTitle("Buscar");
                    alert.setHeaderText("Registro encontrado");
                    alert.showAndWait();
                    txfNombreA.setText(clt.getNombre());
                    txfApellido.setText(clt.getApellidos());
                    txfDireccion.setText(clt.getDireccion());
                }
            }
        });

        btnActualizar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String ID = txfNombre.getText();
                int id = Integer.parseInt(ID);
                String nombre = txfNombreA.getText();
                String apellido = txfApellido.getText();
                String direccion = txfDireccion.getText();
                CRUDCli opCliente = new CRUDCli(accesoBD.getConnection());

                opCliente.updateCliente(id,nombre,apellido,direccion);
                Cliente clt;
                clt = opCliente.getCliente(id);
                alert.setTitle("Actualizar");
                alert.setHeaderText("Actualizacion completada");
                alert.showAndWait();
                txfNombre.setText("");
                txfNombreA.setText("");
                txfApellido.setText("");
                txfDireccion.setText("");
            }
        });

        grid.add(btnBuscar, 4,2);
        grid.add(btnActualizar, 4,4);

        return grid;
    }
    //BORRAR
    private GridPane addGrindPane4() {
        GridPane grid = new GridPane();
        TextField txfNombre = new TextField();
        Label lbNombreA = new Label("---");
        Label lbApellido = new Label("---");
        Label lbDireccion = new Label("---");
        Button btnBuscar = new Button ("Buscar");
        Button btnEliminar = new Button ("Eliminar");
        Label Buscar = new Label ("Buscar con ID");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,10,0,10));

        Text Unidades= new Text("Eliminar");
        Unidades.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(Unidades, 2,1);
        grid.add(Buscar, 2,2);

        txfNombre.setPromptText("Ingrese ID");
        grid.add(txfNombre,3,2);

        grid.add(lbNombreA,1,4);
        grid.add(lbApellido,2,4);
        grid.add(lbDireccion,3,4);

        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String ID = txfNombre.getText();
                int id = Integer.parseInt(ID);
                CRUDCli opCliente = new CRUDCli(accesoBD.getConnection());
                Cliente clt;
                clt = opCliente.getCliente(id);


                if(clt.getNombre().equals(""))
                {
                    alert.setTitle("No encontrado");
                    alert.setHeaderText("No existe registro");
                    alert.showAndWait();
                    lbNombreA.setText("");
                    lbApellido.setText("");
                    lbDireccion.setText("");
                }
                else{
                    alert.setTitle("Buscar");
                    alert.setHeaderText("Registro a eliminar encontrado");
                    alert.showAndWait();
                    lbNombreA.setText(clt.getNombre());
                    lbApellido.setText(clt.getApellidos());
                    lbDireccion.setText(clt.getDireccion());
                }
            }
        });
        btnEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Conexion accesoBD = null;
                try {
                    accesoBD = new Conexion();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String ID = txfNombre.getText();
                int id = Integer.parseInt(ID);
                CRUDCli opCliente = new CRUDCli(accesoBD.getConnection());

                opCliente.deleteCliente(id);

                alert.setTitle("Eliminar");
                alert.setHeaderText("Eliminado");
                alert.showAndWait();

                lbNombreA.setText("---");
                lbApellido.setText("---");
                lbDireccion.setText("---");

            }
        });
        grid.add(btnBuscar, 4,2);
        grid.add(btnEliminar, 4,3);

        return grid;
    }


    public TabPane addPanel() {
        BorderPane border = new BorderPane();
        Tab tab1 = new Tab("Insertar");
        tabpane.getTabs().add(tab1);
        Button botton = new Button("Boton");
        tab1.setContent(botton);

        Tab tab2 = new Tab("Mostrar");
        tabpane.getTabs().add(tab2);

        Tab tab3 = new Tab("Actualizar");
        tabpane.getTabs().add(tab3);

        Tab tab4 = new Tab("Borrar");
        tabpane.getTabs().add(tab4);

        GridPane gridp1 = addGrindPane();
        tab1.setContent(gridp1);

        GridPane gridp2 = addGrindPane2();
        tab2.setContent(gridp2);

        GridPane gridp3 = addGrindPane3();
        tab3.setContent(gridp3);

        GridPane gridp4 = addGrindPane4();
        tab4.setContent(gridp4);

        return tabpane;

    }


    public static void main(String[] args) {
        launch(args);
    }
}
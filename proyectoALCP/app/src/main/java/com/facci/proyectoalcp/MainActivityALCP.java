package com.facci.proyectoalcp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityALCP extends AppCompatActivity {


    DBHelper dbSQLite;

    EditText ID;
    EditText Nombre;
    EditText Apellido;
    EditText Recinto;
    EditText Nacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_alcp);
        dbSQLite = new DBHelper(this);
    }

    public void BTNingresar (View v) {

        Nombre = (EditText) findViewById(R.id.txtNombre);
        Apellido = (EditText) findViewById(R.id.txtApellido);
        Recinto = (EditText) findViewById(R.id.txtRecintoElectoral);
        Nacimiento = (EditText) findViewById(R.id.txtañoNacimiento);

        boolean Insertadatos = dbSQLite.Insertar(Nombre.getText().toString(),Apellido.getText().toString(),Recinto.getText().toString(),Integer.parseInt(Nacimiento.getText().toString()));

        if (Insertadatos) {
            Toast.makeText(MainActivityALCP.this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
        }else{Toast.makeText(MainActivityALCP.this,"Datos no ingresados ocurrió un error",Toast.LENGTH_SHORT).show();}
    }

    public void BTNbuscar (View v) {
        Cursor res = dbSQLite.VerTodos();

        if (res.getCount() == 0) {
            Mensaje("Error","No se encontraron registros");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Id : "+res.getString(0)+"\n");
            buffer.append("Nombre : "+res.getString(1)+"\n");
            buffer.append("Apellido : "+res.getString(2)+"\n");
            buffer.append("Recinto Electoral : "+res.getString(3)+"\n");
            buffer.append("Año de Nacimiento : "+res.getInt(4)+"\n\n");
        }
        Mensaje("Registros",buffer.toString());
    }

    private void Mensaje (String titulo, String Mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(Mensaje);
        builder.show();
    }

    public void BTNmodificar (View v) {
        Nombre = (EditText) findViewById(R.id.txtNombre);
        Apellido = (EditText) findViewById(R.id.txtApellido);
        Recinto = (EditText) findViewById(R.id.txtRecintoElectoral);
        Nacimiento = (EditText) findViewById(R.id.txtañoNacimiento);
        ID = (EditText) findViewById(R.id.txtID);


        boolean ActualizandoDatos = dbSQLite.ModificarRegistro(ID.getText().toString(),Nombre.getText().toString(),Apellido.getText().toString(),Recinto.getText().toString(),Integer.parseInt(Nacimiento.getText().toString()));
        if(ActualizandoDatos)
            Toast.makeText(MainActivityALCP.this,"Datos Ingresados",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivityALCP.this,"Ha ocurrido un error", Toast.LENGTH_SHORT).show();

    }

    public void BTNeliminar (View v) {
        ID = (EditText) findViewById(R.id.txtID);

        Integer registrosEliminados = dbSQLite.Eliminar(ID.getText().toString());

        if(registrosEliminados > 0 ){
            Toast.makeText(MainActivityALCP.this,"Registro(s) Eliminado(s) correctamente",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivityALCP.this,"Error: Registro no eliminado",Toast.LENGTH_SHORT).show();
        }
    }
}

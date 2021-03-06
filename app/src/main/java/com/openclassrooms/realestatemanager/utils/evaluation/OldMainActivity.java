//package com.openclassrooms.realestatemanager.utils.evaluation;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.openclassrooms.realestatemanager.R;
//import com.openclassrooms.realestatemanager.utils.Utils;
//
//public class OldMainActivity extends AppCompatActivity {
//
//    private TextView textViewMain;
//    private TextView textViewQuantity;
//
//    @SuppressLint("CutPasteId")
//	@Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
//        //Pas le bon layout id
//        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main);
//
//        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);
//
//        this.configureTextViewMain();
//        this.configureTextViewQuantity();
//    }
//
//    @SuppressLint("SetTextI18n")
//	private void configureTextViewMain(){
//        this.textViewMain.setTextSize(15);
//        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
//    }
//
//    private void configureTextViewQuantity(){
//        int quantity = Utils.convertDollarToEuro(100);
//        this.textViewQuantity.setTextSize(20);
//        //this.textViewQuantity.setText(quantity);
//        //setText prend un string, pas un int, il faut cast explicitement (si on lui passe un int il va faire comme si c'etait un id de resource)
//        this.textViewQuantity.setText(Integer.toString(quantity));
//    }
//}

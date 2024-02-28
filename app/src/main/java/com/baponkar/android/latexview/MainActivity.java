package com.baponkar.android.latexview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    String [] latexStrings = {"Math Formula -1", "Math Formula-2","Table"};
    String latexFormula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,latexStrings);
        spinner.setAdapter(arrayAdapter);

        LaTexView laTexView = findViewById(R.id.html_webview);


        // Set text color
        laTexView.setTextColor("#FF0000"); // Red color

        // Set text size
        laTexView.setTextSize("20px"); // 20px font size

        // Set background color
        laTexView.setBackgroundColor("#FFFF00"); // Yellow color
        laTexView.setTextFont(getApplicationContext(),R.font.ubuntu_bold);

        latexFormula = "Please select the string by below spinner";
        laTexView.setLatexFormula(latexFormula);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position)
                {
                    case 0:
                        latexFormula = "Einstein energy-mass formula: \\( E=mc^2  \\) and poissons equation \\(  \\nabla^2\\phi(\\vec{r}, t) = 0 \\)" ;
                        break;
                    case 1:
                        latexFormula = "Density \\( \\rho = \\frac{m}{V} \\) where m is mass and V is volume." ;
                        break;
                    case 2:
                        latexFormula = "\\(\\begin{array}{|c|c|c|} \\hline sl. & Variable & Value \\\\ \\hline 1 & x & 3 \\\\ 2 & y & 6 \\\\ \\hline \\end{array}\\)";
                }
                // Set the content with the specified properties
                laTexView.setLatexFormula(latexFormula);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });


        // Set the content with the specified properties
        laTexView.setLatexFormula(latexFormula);

    }
}
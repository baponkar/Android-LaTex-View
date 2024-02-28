package com.baponkar.android.latexview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LaTexView laTexView = findViewById(R.id.html_webview);
        String latexFormula = "\\( E=mc^2 \\int_a^bf(\\frac{\\partial y}{\\partial x})dx = y(x) \\)";

        // Set text color
        laTexView.setTextColor("#FF0000"); // Red color

        // Set text size
        laTexView.setTextSize("20px"); // 20px font size

        // Set background color
        laTexView.setBackgroundColor("#FFFF00"); // Yellow color

        laTexView.setTextFont(getApplicationContext(),R.font.ubuntu_bold);

        // Set the content with the specified properties
        laTexView.setLatexFormula(latexFormula);

    }
}
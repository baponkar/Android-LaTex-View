package com.baponkar.android.latexview;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Base64;
import android.webkit.WebView;

import androidx.annotation.FontRes;
import androidx.core.content.res.ResourcesCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

public class LaTexView extends WebView {

    private String textColor = "#000000"; // Default text color
    private String textSize = "16px"; // Default text size
    private String bgColor = "#FFFFFF"; // Default background color

    public LaTexView(Context context) {
        super(context);
        init();
    }

    public LaTexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LaTexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HTMLWebView);
            textColor = String.valueOf(a.getColor(R.styleable.HTMLWebView_textColor, -1));
            textSize = String.valueOf(a.getDimension(R.styleable.HTMLWebView_textSize, -1));
            bgColor = String.valueOf(a.getColor(R.styleable.HTMLWebView_backgroundColor, -1));
            a.recycle();
        }
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
    }



    // Initialize the WebView with MathJax configuration
    private void init() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true); // Enable local storage for MathJax
    }

    // Method to set the content of the WebView and render LaTeX formula
    public void setContent(String latexFormula) {
        String htmlContent = "<html>" +
                "<head>" +
                "<script src='https://polyfill.io/v3/polyfill.min.js?features=es6'></script>" +
                "<script id='MathJax-script'>" +
                loadScriptFromRaw(getResources().openRawResource(R.raw.tex_mml_chtml)) +
                "</script>" +
                "</head>" +
                "<body>" +
                "<div id='target'>" + latexFormula + "</div>" +
                "</body>" +
                "</html>";
        loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    // Method to set the content of the WebView and render LaTeX formula
    public void setContentWithColors(String latexFormula) {
        String htmlContent = buildHtmlContent(latexFormula);
        loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    public void setLatexFormula(String latexFormula) {
        String htmlContent = buildHtmlContent(latexFormula);
        loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    // Method to set the text color dynamically
    public void setTextColor(String color) {
        textColor = color;
    }

    // Method to set the text size dynamically
    public void setTextSize(String size) {
        textSize = size;
    }

    // Method to set the background color dynamically
    public void setBackgroundColor(String color) {
        bgColor = color;
    }

    public void setTextFont(Context context, @FontRes int fontResourceId) {
        // Get the font family name from the font resource
        Typeface typeface = ResourcesCompat.getFont(context, fontResourceId);
        if (typeface != null) {
            String fontCss = "<style>@font-face {font-family: myFont; src: url('data:font/opentype;base64," +
                    encodeFontAsBase64(typeface) +
                    "');} #target { font-family: myFont; }</style>";
            loadDataWithBaseURL(null, fontCss, "text/html", "UTF-8", null);
        }
    }

    private String encodeFontAsBase64(Typeface typeface) {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        AssetManager assets = getResources().getAssets();
        try (InputStream is = assets.open("myfont.ttf")) {
            int size = is.read(buffer.array());
            return Base64.encodeToString(buffer.array(), 0, size, Base64.NO_WRAP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    // Helper method to build HTML content with dynamic CSS styles and LaTeX formula
    private String buildHtmlContent(String latexFormula) {
        String cssStyles = buildCssStyles();
        return "<html>" +
                "<head>" +
                cssStyles +
                "<script src='https://polyfill.io/v3/polyfill.min.js?features=es6'></script>" +
                "<script id='MathJax-script'>" +
                loadScriptFromRaw(getResources().openRawResource(R.raw.tex_mml_chtml)) +
                "</script>" +
                "</head>" +
                "<body>" +
                "<div id='target'>" + latexFormula + "</div>" +
                "</body>" +
                "</html>";
    }

    // Helper method to build CSS styles dynamically based on set properties
    private String buildCssStyles() {
        return "<style>" +
                "#target {" +
                "    color: " + textColor + ";" +
                "    font-size: " + textSize + ";" +
                "    background-color: " + bgColor + ";" +
                "}" +
                "</style>";
    }

    private String loadScriptFromRaw(InputStream inputStream) {
        BufferedReader reader = null;
        StringBuilder script = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return script.toString();
    }
}

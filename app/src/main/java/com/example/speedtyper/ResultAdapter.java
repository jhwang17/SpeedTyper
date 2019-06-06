package com.example.speedtyper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultAdapter extends ArrayAdapter<Result> {

    private ArrayList<Result> results;
    private Context adapterContext;
    int color;

    public ResultAdapter(Context context, ArrayList<Result> results) {
        super(context, R.layout.list_result, results);
        adapterContext = context;
        this.results = results;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        View v = convertView;
        String rank = String.valueOf(pos + 1);

        switch(pos) {
            case 0:
                rank += "st";
                color = Color.rgb(201, 176, 55);
                break;
            case 1:
                rank += "nd";
                color = Color.rgb(215, 215, 215);
                break;
            case 2:
                rank += "rd";
                color = Color.rgb(173, 138, 86);
                break;
            default:
                rank += "th";
                color = Color.rgb(0, 0, 0);
                break;
        }

        try {
            Result result = results.get(pos);

            if(v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_result, null);
            }

            TextView playerRank = v.findViewById(R.id.textRank);
            TextView playerInitials = v.findViewById(R.id.textPlayerInitials);
            TextView playerScore = v.findViewById(R.id.textScore);

            playerRank.setText(rank);
            playerInitials.setText(result.getPlayerInitials());
            playerScore.setText("" + result.getPlayerScore());

            playerRank.setTextColor(color);
            playerInitials.setTextColor(color);
            playerScore.setTextColor(color);

        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return v;
    }
}

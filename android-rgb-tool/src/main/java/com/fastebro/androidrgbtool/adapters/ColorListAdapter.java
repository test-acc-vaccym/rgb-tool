package com.fastebro.androidrgbtool.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.fastebro.androidrgbtool.R;
import com.fastebro.androidrgbtool.contracts.ColorDataContract;
import com.fastebro.androidrgbtool.events.ColorDeleteEvent;
import com.fastebro.androidrgbtool.utils.UColor;
import com.fastebro.androidrgbtool.view.CircleView;

import de.greenrobot.event.EventBus;

/**
 * Created by daltomare on 17/04/14.
 */
public class ColorListAdapter extends SimpleCursorAdapter {

    public ColorListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        super.bindView(view, context, cursor);

        CircleView color = (CircleView) view.findViewById(R.id.rgb_panel_color);
        TextView rgbValue = (TextView) view.findViewById(R.id.rgb_value);
        TextView hsbValue = (TextView) view.findViewById(R.id.hsb_value);
        ImageButton deleteColor = (ImageButton) view.findViewById(R.id.btn_delete_color);

        final int colorId = cursor.getInt(cursor.getColumnIndex(ColorDataContract.ColorEntry._ID));

        int rgbRValue = cursor.getInt(cursor.getColumnIndex(ColorDataContract.ColorEntry.COLUMN_COLOR_RGB_R));
        int rgbGValue = cursor.getInt(cursor.getColumnIndex(ColorDataContract.ColorEntry.COLUMN_COLOR_RGB_G));
        int rgbBValue = cursor.getInt(cursor.getColumnIndex(ColorDataContract.ColorEntry.COLUMN_COLOR_RGB_B));
        int rgbAValue = cursor.getInt(cursor.getColumnIndex(ColorDataContract.ColorEntry.COLUMN_COLOR_RGB_A));
        float[] hsb;

        rgbValue.setText("(" + rgbAValue + ", " +
                rgbRValue + ", " + rgbGValue + ", " +
                rgbBValue + ")");

        hsb = UColor.RGBToHSB(rgbRValue, rgbGValue, rgbBValue);

        hsbValue.setText("");
        hsbValue.append("(" + String.format("%.0f", hsb[0]));
        hsbValue.append(", " + String.format("%.0f%%", (hsb[1] * 100.0f)));
        hsbValue.append(", " + String.format("%.0f%%", (hsb[2] * 100.0f)) + ")");

        color.setFillColor(Color.argb(rgbAValue, rgbRValue, rgbGValue, rgbBValue));
        color.setStrokeColor(Color.argb(rgbAValue, rgbRValue, rgbGValue, rgbBValue));

        deleteColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ColorDeleteEvent(colorId));
            }
        });
    }
}

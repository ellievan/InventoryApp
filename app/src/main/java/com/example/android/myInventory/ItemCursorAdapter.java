package com.example.android.myInventory;

/**
 * Created by Elena on 24/09/2017.
 */

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myInventory.data.ItemContract;

public class ItemCursorAdapter extends CursorAdapter {

    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    // Makes a new blank list item view
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    //BindView binds item data to a list item
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        //Find views in the list_item
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        ImageView productImageView = (ImageView) view.findViewById(R.id.product_image);
        Button saleButton = (Button) view.findViewById(R.id.sale_button);

        //Find columns of the table
        int nameColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
        final int rowId = cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry._ID));

        productImageView.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE))));

        //Read current item attributes
        String itemName = cursor.getString(nameColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        String itemQuantity = cursor.getString(quantityColumnIndex);

        //Update the text view with the attributes for the current item
        nameTextView.setText(itemName);
        priceTextView.setText(itemPrice);
        quantityTextView.setText(itemQuantity);

        final int currentQuantity = Integer.parseInt(itemQuantity);

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = currentQuantity;
                if (quantity > 0) {
                    quantity = quantity - 1;
                }

                ContentValues contentValues = new ContentValues();
                Uri newUri = ContentUris.withAppendedId(ItemContract.ItemEntry.CONTENT_URI, rowId);
                contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, quantity);
                context.getContentResolver().update(newUri, contentValues, null, null);
            }
        });

    }
}
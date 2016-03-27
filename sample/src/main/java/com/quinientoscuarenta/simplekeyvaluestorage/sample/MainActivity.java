package com.quinientoscuarenta.simplekeyvaluestorage.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.quinientoscuarenta.simplekeyvaluestorage.SimpleKeyValueStorage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_KEY = "key";

    @Bind(R.id.input_string)
    public EditText inputString;
    @Bind(R.id.input_number)
    public EditText inputNumber;
    @Bind(R.id.label_saved_content)
    public TextView labelSavedContent;

    private SimpleKeyValueStorage simpleKeyValueStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        simpleKeyValueStorage = SimpleKeyValueStorage.initDefault(this);
        refreshContent();
    }

    @OnClick(R.id.button_clear)
    public void onClearClick() {
        simpleKeyValueStorage.clear();
        refreshContent();
    }

    @OnClick(R.id.button_delete)
    public void onDeleteClick() {
        simpleKeyValueStorage.delete(PREFS_KEY);
        refreshContent();
    }

    @OnClick(R.id.button_save)
    public void onSaveClick() {
        Content content = new Content(
                inputString.getText().toString(),
                inputNumber.getText().toString().isEmpty()
                        ? 0
                        : Integer.parseInt(inputNumber.getText().toString())
        );
        inputString.setText("");
        inputNumber.setText("");

        simpleKeyValueStorage.set(PREFS_KEY, content);
        refreshContent();
    }

    private void refreshContent() {
        if (simpleKeyValueStorage.isSet(PREFS_KEY)) {
            Content content = simpleKeyValueStorage.get(PREFS_KEY, Content.class);
            labelSavedContent.setText(content.toString());
        } else {
            labelSavedContent.setText("None");
        }
    }
}

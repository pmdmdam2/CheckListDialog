package checkitems.dialog.org.activities;

import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import checkitems.dialog.org.checklistdialog.R;
import checkitems.dialog.org.dialogs.CheckListDialog;
import checkitems.dialog.org.interfaces.DialogResponse;

/**
 * Actividad para mostrar un cuadro de diálogo con lista de selección múltiple
 */
public class CheckListActivity extends AppCompatActivity implements DialogResponse {
    private Button btOnOff;
    private boolean startLight1, startLight2, startLight3, startLight4, startLight5, startLight6;
    private ImageView ivStart1, ivStart2, ivStart3, ivStart4, ivStart5, ivStart6;
    private TextView tvNavidad;
    private CountDownTimer cT;
    private ArrayList<CountDownTimer> timers;
    private boolean startLights;
    private CheckListDialog cld;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        cld = new CheckListDialog();
        btOnOff = findViewById(R.id.btOnOff);
        btOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!startLights) {
                    timers.clear();

                    cld.show(getSupportFragmentManager(), null);
                }else{
                    startLights = false;
                    btOnOff.setText(R.string.crear_ambiente);
                    tvNavidad.setVisibility(View.INVISIBLE);
                    for(CountDownTimer timer:timers){
                        timer.onFinish();
                        timer.cancel();
                    }
                }
            }
        });
        ivStart1 = findViewById(R.id.ivStart1);
        ivStart2 = findViewById(R.id.ivStart2);
        ivStart3 = findViewById(R.id.ivStart3);
        ivStart4 = findViewById(R.id.ivStart4);
        ivStart5 = findViewById(R.id.ivStart5);
        ivStart6 = findViewById(R.id.ivStart6);
        tvNavidad = findViewById(R.id.tvNavidad);
        timers = new ArrayList<CountDownTimer>();
    }

    /**
     * Método de interfaz para el callback entre el cuadro de diálogo y la actividad en la que se
     * aceptan los elementos seleccionados
     * @param checkedItems Elementos seleccionados de la lista
     */
    @Override
    public void onResponse(boolean[] checkedItems) {
        btOnOff.setText(R.string.quitar_ambiente);
        for(int i=0;i<checkedItems.length;i++){
            if(checkedItems[i]){
                setTimer(i+1);
            }
        }
        startLights = true;
        tvNavidad.setVisibility(View.VISIBLE);
    }
    /**
     * Método de interfaz para el callback entre el cuadro de diálogo y la actividad, se cancelan los
     * elementos seleccionados
     */
    @Override
    public void onCancel() {
        startLights = false;
        btOnOff.setText(R.string.crear_ambiente);
    }

    /**
     * Método para temporizar el encendido y apagado de las estrellas del árbol
     * @param checkItem Elemento seleccionado (nivel del árbol)
     */
    private void setTimer(final int checkItem){
       cT = new CountDownTimer(100000, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d",millisUntilFinished/60000);
                int va = (int)((millisUntilFinished%60000)/1000);
                if(checkItem==1){
                    ivStart1.setImageResource(startLight1?android.R.drawable.star_big_on:android.R.drawable.star_big_off);
                    startLight1 = !startLight1;
                }else if(checkItem==2){
                    ivStart2.setImageResource(startLight2?android.R.drawable.star_big_on:android.R.drawable.star_big_off);
                    startLight2 = !startLight2;
                    ivStart3.setImageResource(startLight3?android.R.drawable.star_big_on:android.R.drawable.star_big_off);
                    startLight3 = !startLight3;
                }else if(checkItem==3){
                    ivStart4.setImageResource(startLight4?android.R.drawable.star_big_on:android.R.drawable.star_big_off);
                    startLight4 = !startLight4;
                    ivStart5.setImageResource(startLight5?android.R.drawable.star_big_on:android.R.drawable.star_big_off);
                    startLight5 = !startLight5;
                    ivStart6.setImageResource(startLight6?android.R.drawable.star_big_on:android.R.drawable.star_big_off);
                    startLight6 = !startLight6;
                }
            }
            public void onFinish() {
                ivStart1.setImageResource(android.R.drawable.star_big_off);
                ivStart2.setImageResource(android.R.drawable.star_big_off);
                ivStart3.setImageResource(android.R.drawable.star_big_off);
                ivStart4.setImageResource(android.R.drawable.star_big_off);
                ivStart5.setImageResource(android.R.drawable.star_big_off);
                ivStart6.setImageResource(android.R.drawable.star_big_off);
            }
        };
        timers.add(cT);
        cT.start();

    }
}

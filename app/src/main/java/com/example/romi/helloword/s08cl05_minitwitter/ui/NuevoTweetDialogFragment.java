package com.example.romi.helloword.s08cl05_minitwitter.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.romi.helloword.s08cl05_minitwitter.R;
import com.example.romi.helloword.s08cl05_minitwitter.common.Constantes;
import com.example.romi.helloword.s08cl05_minitwitter.common.SharedPreferencesManager;
import com.example.romi.helloword.s08cl05_minitwitter.data.TweetViewModel;
import com.example.romi.helloword.s08cl05_minitwitter.retrofit.response.Tweets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class NuevoTweetDialogFragment extends DialogFragment implements View.OnClickListener {
    ImageView ivClose, ivAvatar;
    Button  btnTwittear;
    EditText    etMensaje;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View  view = inflater.inflate(R.layout.nuevo_tweet_full_dialog,container, false);

        ivClose     = view.findViewById(R.id.imageViewClose);
        ivAvatar    = view.findViewById(R.id.imageViewAvatar);
        btnTwittear = view.findViewById(R.id.buttonTwitear);
        etMensaje   = view.findViewById(R.id.editTextMensaje);

        //Eventos

        btnTwittear.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        //Seteamos la imagen de usuario de perfil
        String photoUrl = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_PHOTO_URL);

        //Me tengo que acordar que el signo de admiracion adelante es NOT / NEGACION (En este caso si no es vacia la URL)
        if(!photoUrl.isEmpty()){
            Glide.with(getActivity())
                    .load(Constantes.API_MINITWITTER_FILES_URL + photoUrl)
                    .into(ivAvatar);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        //Aca sobreescribimos el onClick y lo primero que hago es ver EN DONDE hice click
        int id = v.getId();
        String mensaje = etMensaje.getText().toString();

        if (id == R.id.buttonTwitear) {
            if(mensaje.isEmpty()){
                Toast.makeText(getActivity(),"Debe insertar un texto en el tweet",Toast.LENGTH_LONG).show();
            }else{
                //Declaro el objeto de tipo data
                TweetViewModel tweetViewModel = ViewModelProviders
                        .of(getActivity()).get(TweetViewModel.class);
                tweetViewModel.insertTweet(mensaje);
                getDialog().dismiss();
            }
        }else if( id == R.id.imageViewClose){
            if(!mensaje.isEmpty()) {
                showDialogConfirm();
            }else{
                getDialog().dismiss();
            }
        }
    }

    private void showDialogConfirm() {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("¿Desea Realmente cancelar el Tweet, el mensaje se borrará")
                .setTitle("Cancelar Tweet");

        // Add the buttons
        //En los botones programo, si el usuario quiere eliminar el tweet cierro ambos dialogos,
        //El que pregunta si lo quiere eliminar y el que estoy usando para escribir el tweet
        // OSEA ESTE
        builder.setPositiveButton("Eliminar.", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                getDialog().dismiss();
            }
        });
        //En este caso el unico dialogo que cierro es el que le pregunta al usuario si quiere
        //cancelar o seguir.
        //Basicamente es como un textbox con dsitintas opciones..
        builder.setNegativeButton("Cancelar.", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();
    }
}

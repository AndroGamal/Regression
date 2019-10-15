package com.example.andro.regression;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public abstract class FolderPath {
    private ListView list;
    private  AlertDialog.Builder show;
    private   ArrayList v;
    private String s,ans;
    boolean chick;
    private Context context;
    private File x;
    private  AlertDialog dialog;
    FolderPath(Context context){
        this.context=context;
        v=new ArrayList();
        show =new AlertDialog.Builder(context);
    }
    void create(){

        v.clear();
        chick=false;
        newlistview();
        s="storage";
        x=new File("storage");
        Collections.addAll(v, x.list());
        list.setAdapter(new ArrayAdapter(context,android.R.layout.simple_expandable_list_item_1,v));
        dialog=   show.setTitle("load file *.xml").setView(list).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(chick){
                    ans=s;
                    OK();
                }
            }
        }).setNegativeButton("Back", null).setCancelable(true).show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int r=s.lastIndexOf('/');
                if(r!=-1)
                {s=s.substring(0,r);
                    back();
                }
                else {dialog.cancel();}
            }
        });

    }
    private void  newlistview(){
        list=new ListView(context);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(chick){
                    s=s.substring(0,s.lastIndexOf('/'));
                }
                s+="/"+v.get(i);
                x=new File(s);
                if(x.isFile())
                {
                    chick=true;
                    Toast.makeText(context,"you are select file" , Toast.LENGTH_LONG).show();
                }
                else {
                    v.clear();
                    Collections.addAll(v, x.list());
                    list.setAdapter(new ArrayAdapter(context,android.R.layout.simple_expandable_list_item_1,v));
                }
            }
        });
    }
    private void back(){
        if(chick){
            s=s.substring(0,s.lastIndexOf('/'));
            chick=false;}
        File x=new File(s);
        v.clear();
        Collections.addAll(v, x.list());
        list.setAdapter(new ArrayAdapter(context,android.R.layout.simple_expandable_list_item_1,v));
    }
    String getpath(){return ans;}

    abstract void OK();
}


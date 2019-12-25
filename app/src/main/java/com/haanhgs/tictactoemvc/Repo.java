package com.haanhgs.tictactoemvc;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Repo {

    public static void save(Context context, Game game){
        try{
            FileOutputStream out = context.openFileOutput("save.txt", Context.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(game);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Game load(Context context){
        Game game = new Game();
        File file = new File(context.getFilesDir(), "save.txt");
        if (file.exists()){
            try{
                FileInputStream in = context.openFileInput("save.txt");
                ObjectInputStream inputStream = new ObjectInputStream(in);
                Object object = inputStream.readObject();
                game = (Game)object;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return game;
    }
}

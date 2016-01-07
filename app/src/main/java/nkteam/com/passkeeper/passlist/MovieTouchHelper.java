package nkteam.com.passkeeper.passlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import nkteam.com.passkeeper.database.DatabaseHandler;
import nkteam.com.passkeeper.database.PKDataModel;

public class MovieTouchHelper extends ItemTouchHelper.SimpleCallback {
    PassAdapter adapter;
    List<PKDataModel> passList;
    DatabaseHandler db;

    public MovieTouchHelper(PassAdapter movieAdapter, Context context){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = movieAdapter;
        db = new DatabaseHandler(context);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        passList = db.getAllDataPasses();

        // position the item where the user pressed a finger
        int position = viewHolder.getAdapterPosition();

        PKDataModel model = passList.get(position);
        db.deleteDataPass(model);

        adapter.remove(viewHolder.getAdapterPosition());
    }
}

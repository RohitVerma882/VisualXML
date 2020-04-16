package coyamo.visualxml;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import coyamo.visualxml.parser.AndroidXmlParser;
import coyamo.visualxml.proxy.ProxyResources;
import coyamo.visualxml.ui.ErrorMessageAdapter;
import coyamo.visualxml.ui.OutlineView;
import coyamo.visualxml.utils.MessageArray;

public class ViewActivity extends AppCompatActivity {
    private OutlineView outlineView;
    private RecyclerView rv;
    private DrawerLayout drawer;
    private LinearLayout drawerSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        drawer = findViewById(R.id.drawerLayout);
        drawerSub = findViewById(R.id.drawer_sub);
        outlineView = findViewById(R.id.outline_view);
        rv = findViewById(R.id.err_list);
        rv.setAdapter(new ErrorMessageAdapter());
        rv.setLayoutManager(new LinearLayoutManager(this));
        ProxyResources.getInstance().getViewIdMap().clear();
        MessageArray.getInstanse().clear();


        try {
            //AndroidXmlParser.with(outlineView).parse(new File(xml));
            AndroidXmlParser.with(outlineView).parse(getIntent().getStringExtra("xml"));

        } catch (Exception e) {
        }
        if (MessageArray.getInstanse().getList().size() > 0) {
            if (!drawer.isDrawerOpen(drawerSub))
                drawer.openDrawer(drawerSub);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(drawerSub)) {
            drawer.closeDrawer(drawerSub);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.debug:
                if (drawer.isDrawerOpen(drawerSub))
                    drawer.closeDrawer(drawerSub);
                else
                    drawer.openDrawer(drawerSub);
                break;
            case R.id.change:
                outlineView.setShowOutlineOnly(!outlineView.isShowOutlineOnly());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view, menu);
        return true;
    }
}

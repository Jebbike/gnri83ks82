package melbet.malbet.hispone;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import melbet.malbet.hispone.plug.Info;
import melbet.malbet.hispone.plug.InfoListAdapter;
import melbet.malbet.hispone.plug.NewsParser;

public class PlugActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.plug_layout);

        //generateDemoInfoList(20, 3, 5, 2);
        List<Info> itemsArrayList = parseNews();

        ListView itemsListView = findViewById(R.id.factsList);

        InfoListAdapter adapter = new InfoListAdapter(PlugActivity.this, itemsArrayList);
        itemsListView.setAdapter(adapter);
    }

    private List<Info> parseNews() {
        try {
            return NewsParser.parse(getAssets().open("default_news.xml"));
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<Info> generateDemoInfoList(int size, int extraSizeCount, int extraDash, int dash) {
        if (size == 0)
            return new ArrayList<>();

        List<Info> infoList = new ArrayList<>();

        String content = getResources().getString(R.string.lorem_ipsum);
        if (content == null) {
            content = "nullable content";
        }

        for (int i = 0; i < extraSizeCount; i++) {
            infoList.add(createRandomInfo(content, extraDash));
        }

        for (int i = 0; i < size; i++) {
            infoList.add(createRandomInfo(content, dash));
        }

        return infoList;
    }

    Info createRandomInfo(String content, int detailDashCount) {
        String details = "";

        for (int i = 0; i < detailDashCount; i++) {
            details = details.concat(PlugActivityUtils.randomDash(content, content));
        }

        return new Info("lorem ipsum", details);
    }

    @VisibleForTesting
    static class PlugActivityUtils {
        @VisibleForTesting
        static String randomDash(String source, String from) {
            if (from.isEmpty())
                return source;
            if (from.length() == 1)
                return source.concat(from);

            int a = ThreadLocalRandom.current().nextInt(0, from.length());
            if (a == 0)
                a = 2;

            return source.concat(from.substring(0, Math.min(from.length(), a)));
        }
    }
}
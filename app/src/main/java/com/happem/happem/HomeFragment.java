package com.happem.happem;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.happem.happem.R.layout.home_layout, container, false);
        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        EventAdapter ca = new EventAdapter(createList(20));
        recList.setAdapter(ca);
        return rootView;
    }

    public List<EventInfo> createList(int size) {

        List<EventInfo> result = new ArrayList<EventInfo>();
        for (int i=1; i <= size; i++) {
            EventInfo ci = new EventInfo();
            ci.name = EventInfo.NAME_PREFIX + i;

            result.add(ci);
        }

        return result;
    }


}

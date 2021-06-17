package com.example.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bottomnavigation.databinding.ActivityMainBinding;

import me.ibrahimsn.lib.OnItemSelectedListener;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        by default in home it shows fragmnet
        FragmentTransaction homeTrans = getSupportFragmentManager().beginTransaction();
        homeTrans.replace(R.id.fragment_container,new HomeFragment());
        homeTrans.commit();


//        custom navbar-1 smooth bar
        binding.bottomNav.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //int i -i is index not menu item lib
                switch (i){
                    case 0:
                        transaction.replace(R.id.fragment_container,new HomeFragment() );
                        break;
                    case 1:
                        transaction.replace(R.id.fragment_container,new CasesFragment());
                        break;
                        case 2:
                            transaction.replace(R.id.fragment_container,new SearchFragment());
                        break;

//                        case 3:
//                            transaction.replace(R.id.fragment_container,new SettingsFragment());
//                        break;

                }
                transaction.commit();
                return false;//but in normal its true.
            }
        });

    }
}
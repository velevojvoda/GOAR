package com.example.goar.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.goar.R;
import com.example.goar.data.model.User;
import com.example.goar.data.repository.FirestoreSeeder;
import com.example.goar.data.repository.SessionManager;
import com.example.goar.databinding.ActivityMainBinding;
import com.example.goar.databinding.NavHeaderBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_map, R.id.nav_pantry, R.id.nav_shopping,
                R.id.nav_profile, R.id.nav_favorites, R.id.nav_my_reports,
                R.id.nav_notifications, R.id.nav_admin)
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
        NavigationUI.setupWithNavController(binding.navView, navController);

        setupDrawer();
        setupBackPress();
        FirestoreSeeder.seedIfEmpty();
    }

    private void setupDrawer() {
        User user = SessionManager.getCurrentUser();
        NavHeaderBinding header = NavHeaderBinding.bind(binding.navView.getHeaderView(0));
        if (user != null) {
            header.textUsername.setText(user.getUsername());
            header.textEmail.setText(user.getEmail());
            header.textRole.setText(user.isAdmin() ? R.string.role_admin : R.string.role_user);
        }

        binding.navView.getMenu().findItem(R.id.nav_admin).setVisible(SessionManager.isAdmin());

        binding.navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                SessionManager.logout();
                Toast.makeText(this, R.string.logged_out, Toast.LENGTH_SHORT).show();
            } else {
                NavigationUI.onNavDestinationSelected(item, navController);
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void setupBackPress() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_notifications) {
            navController.navigate(R.id.nav_notifications);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

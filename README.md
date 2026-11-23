# GOAR – Recipes Android App

Android app for managing recipes, ingredients and shopping lists.
Java, Material Design 3, minSdk 30.

## Project structure

```
com.example.goar
├── ui                      UI layer
│   ├── activities          MainActivity (toolbar, bottom navigation, drawer, NavHostFragment)
│   ├── fragments/<feature> one package per feature: Fragment + its ViewModel
│   └── adapters            RecyclerView adapters
└── data                    Data layer
    ├── model               shared entities (Recipe, Ingredient, User...)
    └── repository          data access (MockData for now, database later)
```

Navigation graph: `res/navigation/base_navigation.xml`
Menus: `res/menu/bottom_nav_menu.xml`, `drawer_menu.xml`, `toolbar_menu.xml`
Theme and colors: `res/values/themes.xml`, `res/values/colors.xml`, `res/values-night/colors.xml`

## Who owns what

| Package (`ui/fragments/...`) | Owner | Spec |
|---|---|---|
| `profile`, `recipe`, `auth`, cooking mode | Student 1 | 1, 2, 2.1, 2.2, 3, 3.1, 4, 5, 14 |
| `search`, `pantry`, `shopping`, `reports`, ratings & comments | Student 2 | 6, 6.1, 8, 9, 9.1, 10, 10.1, 11, 11.1, 11.2 |
| `home`, `map`, `notifications`, `admin` | Student 3 | 3.2, 3.3, 7, 12, 12.1, 12.2, 13, 15 |

Shared (agree before changing): `data/model`, theme and colors, `base_navigation.xml`, menus, `MainActivity`.

## Git workflow

- `main` always builds and runs.
- Everyone works on their own branch: `student1/<feature>`, `student2/<feature>`, `student3/<feature>`.
- Before starting work: `git checkout main` and `git pull`, then create or update your branch.
- When a feature is done, merge it into `main` through a pull request.
- Do not commit `.idea/gradle.xml` or other IDE files that change on every machine.

## Rules

- Do not change other students' packages.
- Keep screen state (search text, filters, sorting) in the fragment's ViewModel with LiveData, not in the fragment, so it survives navigation and screen rotation.
- Use ViewBinding instead of `findViewById`.
- All UI text goes to `strings.xml`, in English.
- Use theme colors (`?attr/colorPrimary`, `?attr/colorSurface`...) instead of hardcoded colors.
- To add a screen: create the fragment in your package and add it to `base_navigation.xml`.

## Mock data

- `MockData` holds shared test data (users, types, categories, allergens, ingredients, recipes).
- `SessionManager` holds the current user. Until login is implemented (Student 1), the user comes from `MockData`.
- `MockData.CURRENT_USER_IS_ADMIN`: `true` shows the app as admin (Admin panel visible), `false` as a regular user.
- Logout is a placeholder until Student 1 implements authentication.

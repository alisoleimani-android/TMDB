# TMDB android project

### 3rd-Party Libraries

- Used **material** for designing layouts and UI.

- Used **viewmodel-ktx** for keeping the state of fragments during configuration changes.

- Used **StateFlow** as a data holder for observing the screen's state changes.

- Used **retrofit2** for networking and API calls.

- Used **okhttp3** as a client agent and also defined interceptors.

- Used **moshi-kotlin** as a JSON parser and also defined custom adapters.

- Used **glide** for loading images.

- Used **navigation** for navigating through fragments.

- Used **hilt** for using dependency injection all over the app.

- Used **kotlinx-coroutines** for using the concurrency design pattern and doing asynchronous jobs.

- Used **timber** for logging within the app.


- Used **junit** for writing unit tests.

- Used **truth** for easy assertion in tests.

- Used **mockito-kotlin** for mocking.

- Used **turbine** for testing flows.

- Used **kotlinx-coroutines-test** for testing coroutines.

- Used **kotlinx-datetime** for dealing with date and time (please check out AppRepositoryImpl for more information)


### Optional features:

- **Pagination:** In the PopularMoviesFragment, the list is displayed to the user by pagination. But the Paging 3 library has not been used to implement this feature! Why?
  Because Paging 3 is very complicated and weird! For example, if you want to filter the data or change the color of one row of the list for any reason, you will face a severe challenge. For this reason, I have used a straightforward and, at the same time, flexible and testable solution that you can see in the **DefaultPaginator** class.

- **Night/Day mode:** On PopularMoviesFragment, there is a button in the toolbar to change the theme to night and day mode. The composition of SharedPreferences and Flow has been used to implement this feature.
  Learn more about the implementation on **AppPreferences**.


### Architecture:

I decided to develop this app by using **MVVM clean architecture**. And here are my top 3 reasons for using it:

1. **Low coupling:** Build things in a distributed manner. It's all about the **separation of concerns**. You can also use multiple view models inside a single view.

2. **Test Friendly:** ViewModel has no reference to theView (Activity/Fragment) and can be tested easier than MVP for instance.

3. **Understand the app easier:** Domain layer tells you everything! what this app is all about and also what it can do for the user!


### Package structure:

- **appinitializers:** All the modules used throughout the app are included in this package. To learn more, please check out AppInitializers class.

- **di:** All the classes that are used throughout the app are provided here.

- **common:** This package includes all classes (such as utils, data, domain, presentation, etc.) and modules common to all features.

- **movie:** This package includes all classes (such as data, domain, presentation, etc.) related to movie features like popular shows screen and detail screen.
# FavQs-Quote-App

**FavQs Quote App** is an Android application that allows users to explore, view, and share inspiring quotes. The app interacts with the **FavQs API**, fetching random quotes from a variety of authors and categories. It is designed to be simple, user-friendly, and efficient, providing users with a seamless experience for browsing motivational quotes.

## Features
- **Random Quote Fetching**: Displays random quotes from the FavQs API on demand.
- **Author Information**: Provides details about the author of each quote.
- **Sharing Quotes**: Users can share quotes directly from the app to social media or other platforms.
- **UI/UX**: Clean and intuitive interface for smooth navigation and interaction.

## Technology Stack
- **Programming Language**: Java/Kotlin
- **IDE**: Android Studio
- **API Integration**: FavQs API
- **Design Pattern**: MVVM (Model-View-ViewModel)
- **Libraries Used**:
  - Retrofit: For API integration and network calls
  - Glide: For loading and displaying images (if applicable)
  - RecyclerView: To display quotes in a list format
  - Material Design Components: For UI elements and improved user experience

## How It Works
1. **API Integration**:
   - The app integrates with the **FavQs API**, which provides a vast collection of quotes.
   - A network call is made using Retrofit to fetch a random quote.
   
2. **Displaying Quotes**:
   - Once the quote is fetched, it is displayed on the screen.
   - The app provides the author’s name and the quote text in a clean layout.

3. **Sharing Option**:
   - Users can click the **Share** button to share the current quote via different social media platforms, email, or messaging apps.

## How to Run the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/Gops0/FavQs-Quote-App.git
   ```
2. Open the project in Android Studio.
3. Build and run the project on an Android emulator or physical device.

## API Usage
The app uses the [FavQs API](https://favqs.com/api) to retrieve quotes. Here’s how the API is utilized:
- **GET Random Quote**: Fetches a random quote from the API.
  
Example API Call:
```bash
GET https://favqs.com/api/qotd
   ```

## Screenshots

![Screenshot 1](favqs_Splash.jpeg)
*Home screen with random quote*

![Screenshot 2](favqs.jpeg)
*Refreshing to get a new quote*





Features

Use of RecyclerView and GridLayout: Display a 3-column square image grid. Images are center-cropped within each image item.
Image Loading:
Implemented asynchronous image loading using Retrofit and URL.
The API response contains a thumbnail object in each array element to display images.
Display: Users can scroll through 100 images.
Caching: Utilize Glide for caching and disk caching.
Error Handling: Gracefully handle network errors and image loading failures when fetching images from the API, providing informative error messages.


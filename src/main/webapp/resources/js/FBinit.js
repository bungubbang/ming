/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 9/8/13
 */
(function () {
    // Initialize the Facebook JavaScript SDK
    FB.init({
        appId: '403677719733998',
        xfbml: true,
        status: true,
        cookie: true
    });

    // Check if the current user is logged in and has authorized the app
    FB.getLoginStatus(checkLoginStatus);


    // Check the result of the user status and display login button if necessary
    function checkLoginStatus(response) {
        if(response && response.status != 'connected') {
            location.href="/login";
        } else {
            start();
        }
    }
})();
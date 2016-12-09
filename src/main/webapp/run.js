/**
 * Created by i-tang on 02/09/16.
 */
var curl;
(function () {
    curl({
        main: 'js/hsking/main',
        packages: {
            // HSKING's package
            hsking: { location: 'js/hsking' },
            // Third-party packages
            curl: { location: 'lib/curl/src/curl' },
            jquery: { location: 'lib/jquery/dist/jquery', main: '.' },
            Backbone: { location: 'lib/backbone-amd/backbone', main: '.' },
            underscore: { location: 'lib/lodash/lodash', main: '.' }
        }
    });

}());
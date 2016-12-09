/**
 * Created by i-tang on 02/09/16.
 */
define(function (require) {

    var $ = require('jquery');
    var MainView = require('./views/MainView');

    var self = this;
    $(document).ready(function () {
        // Create whole page view instance and render it.
        new MainView({
            el: 'body'
        }).render();
    });
});
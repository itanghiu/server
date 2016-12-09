/**
 * Created by i-tang on 10/09/16.
 */

define(function (require) {

    var Backbone = require('Backbone');

    var ICsts = Backbone.Model.extend({

        serverUrl : 'http://localhost:9999/hsking',

        SERVER_URL : function() {
            return this.serverUrl;
        }
    });
    return ICsts;
});

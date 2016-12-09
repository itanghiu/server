/**
 * Created by i-tang on 03/09/16.
 */
define(function (require) {
    var Backbone = require('Backbone');

    var HskListModel = Backbone.Model.extend({

        print: function () {
            return 'name:' + this.get('name')
                + ' bookName:' + this.get('bookName')
                + ' volumeNumber: ' + this.get('volumeNumber');
        }
    });
    return HskListModel;
});
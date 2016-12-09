/**
 * Created by i-tang on 10/09/16.
 */
define(function (require) {
    var Backbone = require('Backbone');

    var HskWordModel = Backbone.Model.extend({

        print: function () {
            return 'id:' + this.get('id')
                + ', simplified:' + this.get('simplified')
                + ', pinyin:' + this.get('pinyin')
                + ', cedictMeaning: ' + this.get('cedictMeaning');
        }
    });
    return HskWordModel;
});
define(function (require) {
    var Backbone = require('Backbone');
    var ICsts = require('../ICsts');
    var User = require('./HskListModel');

    var icsts = new ICsts();
    var HskListCollection = Backbone.Collection.extend({

        model: User,
        urlRoot: icsts.SERVER_URL() + "/hskLists",

        url: function () {
            return this.urlRoot;
        },

        print: function () {
            var result = "";
            this.forEach(function (hskList) {
                result += hskList.print();
            });
            return result;
        }
    });
    return HskListCollection;
});
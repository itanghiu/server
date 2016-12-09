/**
 * Created by i-tang on 07/09/16.
 */

// Define new view to render whole page.
define(function (require) {

    var Backbone = require('Backbone');

    var UserCollectionView = require('./UserCollectionView');
    var UserCollection = require('../models/UserCollection');
    var UserListButtonView = require('./UserListButtonView');

    var HskListCollection = require('../models/HskListCollection');
    var HskListCollectionView = require('./HskListCollectionView');
    var HskListButtonView = require('./HskListButtonView');
    var $ = require('jquery');

    var userCollection = new UserCollection();
    userCollection.fetch({
        success: function (model, response, options) {
            console.log('User Fetch success');
        },
        error: function (model, response, options) {
            console.log('User Fetch error');
        },
        reset: true
    });

    var hskListCollection = new HskListCollection({ id: document.location.search.slice(1) });
    hskListCollection.fetch({
        success: function (model, response, options) {
            console.log('hskList Fetch success');
        },
        error: function (model, response, options) {
            console.log('hskList Fetch error');
        },
        reset: true
    });

    var MainView = Backbone.View.extend({

        initialize: function () {
            console.log("hskListCollection: " + hskListCollection);
        },

        render: function () {

            new UserCollectionView({ collection: userCollection });
            new UserListButtonView({ el: '#userButtons', collection: userCollection });

            new HskListCollectionView({ collection: hskListCollection });
            new HskListButtonView({ el: '#listButtons', collection: hskListCollection });
        }
    });
    return MainView;
})
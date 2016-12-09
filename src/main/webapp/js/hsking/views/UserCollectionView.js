/**
 * Created by i-tang on 08/09/16.
 */
define(function (require) {
    var Backbone = require('Backbone');
    var UserView = require('./UserView');
    var $ = require('jquery');
    var _ = require('underscore');

    return Backbone.View.extend({

        el: '#users table',

        initialize: function () {
            this.collection.on("reset", this.render, this);// to update the view after the fetch has completed
            this.collection.on("add", this.append, this);// to update the view after a user has been added
            this.render();
        },

        render: function () {

            $(this.el).empty();
            // Append table with a table header.
            $(this.el).append($('<tr></tr>').html(
                _.map(['Name', 'First name', 'Last name', 'Edit', 'Delete'], function (val, key) {
                    return '<th>' + val + '</th>'
                })
            ));
            // adds rows.
            _.each(this.collection.models, function (model, key) {
                this.append(model);
            }, this);
            return this;
        },

        // Add user item row to the table.
        append: function (model) {
            $(this.el).append(
                new UserView({ model: model, collection: this.collection}).render().el
            );
        }
    });
});
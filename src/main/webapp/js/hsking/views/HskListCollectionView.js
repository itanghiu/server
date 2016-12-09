/**
 * Created by i-tang on 02/09/16.
 */
define(function(require) {

    var Backbone = require('Backbone');
    var HskListView = require('./HskListView');
    var $ = require('jquery');
    var _ = require('underscore');

    return Backbone.View.extend({

        el: '#lists table',

        initialize: function() {
            this.collection.on("reset", this.render, this);// to update the view after the fetch has completed
            this.collection.on("add", this.append, this);// to update the view after a user has been added
            this.render();
        },

        render: function() {

            // Append table with a table header.
            $(this.el).append($('<tr></tr>').html(
                _.map(['Name', 'Book name', 'Volume Nbr'], function(val, key){
                    return '<th>' + val + '</th>'
                })
            ));
            // Append table  with a row.
            _.each(this.collection.models, function(model, key) {
                this.append(model);
            }, this);
            return this;
        },

        // Add hskList item row to the table.
        append: function(model) {
            $(this.el).append(
                new HskListView({ model: model }).render().el
            );
        }
    });
});
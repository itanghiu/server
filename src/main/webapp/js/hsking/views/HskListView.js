/**
 * Created by i-tang on 04/09/16.
 */
define(function (require) {

    var Backbone = require('Backbone');
    var $ = require('jquery');

    var HskListView = Backbone.View.extend({

        tagName: 'tr',// Define element tag name.

        initialize: function () {
            this.listenTo(this.model, 'destroy', this.destroy, this);
        },

        destroy: function () {
            this.remove();
        },

        render: function () {
            // Add cells to the table row.append
            console.log("this.model.get('name'):"+ this.model.get('name'))
            $(this.el).html(_.map([
                this.model.get('name'),
                this.model.get('bookName'),
                this.model.get('volumeNbr')
            ], function (val, key) {
                return '<td>' + val + '</td>'
            }));
            return this;
        }
    });
    return HskListView;
})
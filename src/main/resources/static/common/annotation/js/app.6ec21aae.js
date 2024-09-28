(function(e) {
    function t(t) {
        for (var n, o, l = t[0], r = t[1], c = t[2], g = 0, m = []; g < l.length; g++) o = l[g], Object.prototype.hasOwnProperty.call(i, o) && i[o] && m.push(i[o][0]), i[o] = 0;
        for (n in r) Object.prototype.hasOwnProperty.call(r, n) && (e[n] = r[n]);
        d && d(t);
        while (m.length) m.shift()();
        return s.push.apply(s, c || []), a()
    }

    function a() {
        for (var e, t = 0; t < s.length; t++) {
            for (var a = s[t], n = !0, l = 1; l < a.length; l++) {
                var r = a[l];
                0 !== i[r] && (n = !1)
            }
            n && (s.splice(t--, 1), e = o(o.s = a[0]))
        }
        return e
    }
    var n = {},
        i = {
            app: 0
        },
        s = [];

    function o(t) {
        if (n[t]) return n[t].exports;
        var a = n[t] = {
            i: t,
            l: !1,
            exports: {}
        };
        return e[t].call(a.exports, a, a.exports, o), a.l = !0, a.exports
    }
    o.m = e, o.c = n, o.d = function(e, t, a) {
        o.o(e, t) || Object.defineProperty(e, t, {
            enumerable: !0,
            get: a
        })
    }, o.r = function(e) {
        "undefined" !== typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {
            value: "Module"
        }), Object.defineProperty(e, "__esModule", {
            value: !0
        })
    }, o.t = function(e, t) {
        if (1 & t && (e = o(e)), 8 & t) return e;
        if (4 & t && "object" === typeof e && e && e.__esModule) return e;
        var a = Object.create(null);
        if (o.r(a), Object.defineProperty(a, "default", {
                enumerable: !0,
                value: e
            }), 2 & t && "string" != typeof e)
            for (var n in e) o.d(a, n, function(t) {
                return e[t]
            }.bind(null, n));
        return a
    }, o.n = function(e) {
        var t = e && e.__esModule ? function() {
            return e["default"]
        } : function() {
            return e
        };
        return o.d(t, "a", t), t
    }, o.o = function(e, t) {
        return Object.prototype.hasOwnProperty.call(e, t)
    }, o.p = "/";
    var l = window["webpackJsonp"] = window["webpackJsonp"] || [],
        r = l.push.bind(l);
    l.push = t, l = l.slice();
    for (var c = 0; c < l.length; c++) t(l[c]);
    var d = r;
    s.push([0, "chunk-vendors"]), a()
})({
    0: function(e, t, a) {
        e.exports = a("56d7")
    },
    "1be7": function(e, t, a) {
        "use strict";
        a("442e")
    },
    "2a74": function(e, t, a) {
        "use strict";
        a.r(t);
        a("4160"), a("d3b7"), a("ac1f"), a("5319"), a("159b"), a("ddb0");
        var n = a("d307"),
            i = {};
        n.keys().forEach((function(e) {
            "./index.js" !== e && (i[e.replace(/(\.\/|\.js)/g, "")] = n(e).default)
        })), t["default"] = i
    },
    "442e": function(e, t, a) {},
    "4ee2": function(e, t, a) {},
    "56d7": function(e, t, a) {
        "use strict";
        a.r(t);
        a("99af"), a("e260"), a("e6cf"), a("cca6"), a("a79d");
        var n = a("2b0e"),
            i = function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    attrs: {
                        id: "app"
                    }
                }, [a("router-view")], 1)
            },
            s = [],
            o = (a("5c0b"), a("2877")),
            l = {},
            r = Object(o["a"])(l, i, s, !1, null, null, null),
            c = r.exports,
            d = a("8c4f"),
            g = function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    attrs: {
                        id: "content-main"
                    }
                }, [a("div", {
                    staticClass: "wrap_tool main"
                }, [a("commonHeader"), a("div", {
                    staticClass: "inner"
                }, [a("section", {
                    staticClass: "open_main"
                }, [a("ul", [a("li", [a("router-link", {
                    class: "lk_obj",
                    attrs: {
                        to: "/tool",
                        title: "Bounding Box"
                    }
                }, [a("i"), e._v("Bounding Box ")])], 1), a("li", [a("router-link", {
                    class: "lk_veri",
                    attrs: {
                        to: "/verify",
                        title: "Verification"
                    }
                }, [a("i"), e._v("Verification ")])], 1)])])])], 1)])
            },
            m = [],
            u = {
                name: "tool-index",
                methods: {},
                mounted: function() {}
            },
            h = u,
            v = Object(o["a"])(h, g, m, !1, null, null, null),
            f = v.exports,
            p = function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    attrs: {
                        id: "content-main"
                    }
                }, [a("div", {
                    staticClass: "wrap_tool"
                }, [a("commonHeader"), a("div", {
                    staticClass: "to_headersub"
                }, [a("div", {
                    staticClass: "to_btn_wrap",
                    staticStyle: {
                        float: "left"
                    }
                }, [a("button", {
                    staticClass: "btn_to upload",
                    attrs: {
                        type: "button"
                    },
                    on: {
                        click: e.fnOpenFileDialog
                    }
                }, [e._v("파일 열기")]), a("button", {
                    staticClass: "btn_to",
                    attrs: {
                        type: "button"
                    },
                    on: {
                        click: e.fnOpenSavePoup
                    }
                }, [e._v("저장")])])]), a("div", {
                    staticClass: "inner"
                }, [a("input", {
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file",
                        multiple: "multiple",
                        id: "imageFiles",
                        accept: "image/*"
                    },
                    on: {
                        change: e.fnImageLoaded
                    }
                }), a("div", {
                    staticClass: "tool_box left"
                }, [a("div", {
                    staticClass: "upload_list",
                    attrs: {
                        "data-simplebar": ""
                    }
                }, [a("ul", e._l(e.imageList, (function(t, n) {
                    return a("li", {
                        key: n,
                        on: {
                            click: function(t) {
                                return e.fnSelectImage(n)
                            }
                        }
                    }, [a("div", {
                        staticClass: "upload_thumb"
                    }, [a("div", {
                        staticClass: "thumb_img",
                        class: t.selected ? "current" : ""
                    }, [a("div", {
                        staticClass: "thumb_img_brd hover"
                    }), a("img", {
                        attrs: {
                            id: "image" + n
                        }
                    })]), a("div", {
                        staticClass: "thumb_name"
                    }, [a("span", {
                        attrs: {
                            title: t.fileName
                        }
                    }, [e._v(e._s(t.fileName))])])])])
                })), 0)])]), a("div", {
                    staticClass: "tool_box center"
                }, [a("div", {
                    staticClass: "tool_head"
                }, [a("div", {
                    staticClass: "head_util"
                }, [a("div", {
                    staticClass: "tool_opt_list"
                }, [e._m(0), a("ul", {
                    staticClass: "tool_zoom_items"
                }, [a("li", [a("em", {
                    staticClass: "tool_option plus",
                    attrs: {
                        title: "확대"
                    },
                    on: {
                        click: e.fnZoomIn
                    }
                }), a("i", {
                    staticClass: "blind"
                }, [e._v("확대")])]), a("li", [a("em", {
                    staticClass: "tool_option minus",
                    attrs: {
                        title: "축소"
                    },
                    on: {
                        click: e.fnZoomOut
                    }
                }), a("i", {
                    staticClass: "blind"
                }, [e._v("축소")])]), a("li", [a("em", {
                    staticClass: "tool_option reset",
                    attrs: {
                        title: "원래대로"
                    },
                    on: {
                        click: e.fnInitScale
                    }
                }), a("i", {
                    staticClass: "blind"
                }, [e._v("원래대로")])])])]), a("div", {
                    staticClass: "tool_opt_move",
                    class: [e.zoomCount > 0 ? "active" : "", e.isMoveMode ? "on" : ""],
                    attrs: {
                        title: "이동"
                    },
                    on: {
                        click: e.fnChangeModeMove
                    }
                }, [a("i", {
                    staticClass: "blind"
                }, [e._v("이동")])])]), a("div", {
                    staticClass: "head_file"
                }, [a("span", [e._v("파일명")]), a("input", {
                    directives: [{
                        name: "model",
                        rawName: "v-model",
                        value: e.selectedImage.newFileName,
                        expression: "selectedImage.newFileName"
                    }],
                    staticClass: "form_file_name",
                    attrs: {
                        type: "text",
                        name: ""
                    },
                    domProps: {
                        value: e.selectedImage.newFileName
                    },
                    on: {
                        input: function(t) {
                            t.target.composing || e.$set(e.selectedImage, "newFileName", t.target.value)
                        }
                    }
                }), a("em", [e._v("." + e._s(e.selectedImage.fileExtension))])])]), a("div", {
                    staticClass: "tool_body"
                }, [a("div", {
                    directives: [{
                        name: "show",
                        rawName: "v-show",
                        value: e.imageLoading || e.saveLoading,
                        expression: "imageLoading || saveLoading"
                    }],
                    staticClass: "loading_container"
                }, [a("div", {
                    staticClass: "loader"
                }), e.imageLoading ? a("p", {
                    staticStyle: {
                        "margin-top": "10px",
                        color: "#f7f7f7"
                    }
                }, [e._v("이미지를 불러오는 중입니다...")]) : e.saveLoading ? a("p", {
                    staticStyle: {
                        "margin-top": "10px",
                        color: "#f7f7f7"
                    }
                }, [e._v("데이터셋 저장 중입니다...")]) : e._e()]), a("div", {
                    staticClass: "canvas_container",
                    attrs: {
                        id: "canvasContainer"
                    }
                }, e._l(e.imageList, (function(t, n) {
                    return a("canvas", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.selectedImageIndex === n,
                            expression: "selectedImageIndex === index"
                        }],
                        key: n,
                        attrs: {
                            id: "labelCanvas" + n
                        }
                    })
                })), 0)]), a("div", {
                    staticClass: "tool_foot"
                }, [a("div", {
                    staticClass: "foot_status"
                }, [a("div", {
                    directives: [{
                        name: "show",
                        rawName: "v-show",
                        value: e.statusSaved,
                        expression: "statusSaved"
                    }],
                    staticClass: "complete_icon"
                }), e._v(" " + e._s(e.statusText) + " ")])])]), a("div", {
                    staticClass: "tool_box right"
                }, [a("div", {
                    staticClass: "tool_label"
                }, [a("div", {
                    staticClass: "label_tp"
                }, [a("div", {
                    staticClass: "tool_label_tit"
                }, [e._v("라벨 목록")]), a("div", {
                    staticClass: "label_tool_sel"
                }, [e._m(1), a("div", {
                    staticClass: "label_tool_list"
                }, [a("em", {
                    staticClass: "label_tool select",
                    attrs: {
                        title: "Select 툴"
                    },
                    on: {
                        click: function(t) {
                            return e.fnAddLabel("S")
                        }
                    }
                }, [a("span", [e._v("Select툴")])]), a("em", {
                    staticClass: "label_tool crop",
                    attrs: {
                        title: "Crop 툴"
                    },
                    on: {
                        click: function(t) {
                            return e.fnAddLabel("C")
                        }
                    }
                }, [a("span", [e._v("Crop툴")])])])])]), a("div", {
                    staticClass: "label_cont"
                }, [a("div", {
                    staticClass: "label_list",
                    attrs: {
                        "data-simplebar": ""
                    }
                }, [a("ul", e._l(e.selectedImage.labelList, (function(t, n) {
                    return a("li", {
                        key: n
                    }, [a("div", {
                        staticClass: "label_wrap"
                    }, [a("div", {
                        staticClass: "label_input"
                    }, [a("input", {
                        directives: [{
                            name: "model",
                            rawName: "v-model",
                            value: t.text,
                            expression: "label.text"
                        }],
                        staticClass: "label_text_input",
                        attrs: {
                            id: "labelText" + n,
                            type: "text",
                            readonly: !t.selected,
                            value: ""
                        },
                        domProps: {
                            value: t.text
                        },
                        on: {
                            focus: function(t) {
                                return e.fnSelectLabel(n)
                            },
                            input: function(a) {
                                a.target.composing || e.$set(t, "text", a.target.value)
                            }
                        }
                    })]), a("div", {
                        staticClass: "label_bounding"
                    }, e._l(t.boundingBoxes, (function(t, i) {
                        return a("em", {
                            key: i,
                            class: t.selected ? "selected" : "",
                            attrs: {
                                title: "클릭 시 해당 바운딩박스가 삭제됩니다."
                            },
                            on: {
                                mouseover: function(t) {
                                    return e.fnHoverBbox(n, i)
                                },
                                mouseleave: function(t) {
                                    return e.fnLeaveBbox(n, i)
                                },
                                click: function(t) {
                                    return e.fnDeleteBbox(n, i)
                                }
                            }
                        })
                    })), 0), a("div", {
                        staticClass: "label_control"
                    }, [a("button", {
                        staticClass: "btn_label save",
                        attrs: {
                            type: "button",
                            title: "완료"
                        },
                        on: {
                            click: function(t) {
                                return e.fnCompleteLabel(n)
                            }
                        }
                    }, [a("i", {
                        staticClass: "blind"
                    }, [e._v("완료")])]), a("button", {
                        staticClass: "btn_label delete",
                        attrs: {
                            type: "button",
                            title: "삭제"
                        },
                        on: {
                            click: function(t) {
                                return e.fnDeleteLabel(n)
                            }
                        }
                    }, [a("i", {
                        staticClass: "blind"
                    }, [e._v("삭제")])])])])])
                })), 0)])])])])])], 1), e.imageLoading || e.saveLoading ? a("div", {
                    staticClass: "whole_dim"
                }) : e._e(), a("datasetSavePopup", {
                    attrs: {
                        eventBusID: "AnnotationToolDatasetSaveEvent"
                    }
                })], 1)
            },
            b = [function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    staticClass: "option_head"
                }, [a("em", {
                    staticClass: "tool_option default",
                    attrs: {
                        title: "축소/확대 선택"
                    }
                }), a("i", {
                    staticClass: "blind"
                }, [e._v("축소/확대 선택")])])
            }, function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    staticClass: "label_tool add",
                    attrs: {
                        title: "라벨 추가"
                    }
                }, [a("span", [e._v("추가")])])
            }],
            I = (a("4de4"), a("4160"), a("baa5"), a("45fc"), a("a434"), a("b0c0"), a("b64b"), a("d3b7"), a("ac1f"), a("3ca3"), a("5319"), a("159b"), a("ddb0"), a("2b3d"), a("96cf"), a("1da1")),
            w = a("1157"),
            _ = a.n(w),
            C = a("c4e3"),
            x = a.n(C),
            M = a("21a6"),
            L = a.n(M);

        function y(e, t) {
            return S.apply(this, arguments)
        }

        function S() {
            return S = Object(I["a"])(regeneratorRuntime.mark((function e(t, a) {
                var n;
                return regeneratorRuntime.wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, R(t);
                        case 2:
                            return n = e.sent, e.abrupt("return", E(n, a));
                        case 4:
                        case "end":
                            return e.stop()
                    }
                }), e)
            }))), S.apply(this, arguments)
        }

        function E(e, t) {
            var a = t.x,
                n = t.y,
                i = t.width,
                s = t.height,
                o = document.createElement("canvas");
            o.width = i, o.height = s;
            var l = o.getContext("2d");
            l.drawImage(e, a, n, i, s, 0, 0, i, s);
            var r = o.toDataURL("image/png");
            return r.replace(/^data:image\/(png|jpg);base64,/, "")
        }

        function R(e) {
            var t = new FileReader,
                a = new Image;
            return new Promise((function(n, i) {
                t.onerror = function() {
                    t.abort(), i(new DOMException("Loading error."))
                }, t.onload = function(e) {
                    a.onload = function() {
                        n(a)
                    }, a.src = e.target.result
                }, t.readAsDataURL(e)
            }))
        }
        var O = {
                name: "annotation-tool",
                components: {},
                data: function() {
                    return {
                        datasetInfo: {},
                        initCanvasWidth: 0,
                        initCanvasHeight: 0,
                        windowWidthRatio: 1,
                        windowHeightRatio: 1,
                        statusText: "이미지를 선택해주세요.",
                        statusSaved: !1,
                        image: null,
                        saveLoading: !1,
                        imageLoading: !1,
                        canvas: null,
                        context: null,
                        mouseIsDown: !1,
                        canvasInterval: null,
                        rectangles: [],
                        imageList: [],
                        selectedImage: {},
                        selectedImageIndex: -1,
                        labelList: [],
                        selectedLabel: {},
                        selectedBbox: {},
                        startX: 0,
                        startY: 0,
                        endX: 0,
                        endY: 0,
                        canvasScaleFactor: 1.1,
                        scaleFactor: null,
                        zoomCount: 0,
                        zoomInExp: 3,
                        zoomOutExp: -3,
                        imageMoveX: 0,
                        imageMoveY: 0,
                        isMoveMode: !1,
                        newLabelSelectType: {
                            selected: !0,
                            type: "S",
                            text: "",
                            boundingBoxes: []
                        },
                        newLabelCropType: {
                            selected: !0,
                            type: "C",
                            text: "",
                            boundingBox: {}
                        }
                    }
                },
                computed: {
                    isAllLabelCompleted: function() {
                        var e = this,
                            t = !0;
                        return e.selectedImage.labelList && e.selectedImage.labelList.length > 0 && e.selectedImage.labelList.forEach((function(e) {
                            e.selected && (t = !1)
                        })), t
                    }
                },
                methods: {
                    fnGetDatasetSaveInfo: function(e) {
                        this.datasetInfo = this.deepCopy(e), this.fnSaveDataset()
                    },
                    fnOpenSavePoup: function() {
                        var e = this;
                        if (!e.selectedImage.labelList || e.selectedImage.labelList.length < 1) e.alert("저장할 라벨 정보가 없습니다.");
                        else {
                            var t = !1;
                            if (e.imageList.some((function(e) {
                                    if (!e.labelList || e.labelList.length < 1) return t = !0, !0
                                })), e.isAllLabelCompleted) {
                                var a = !0;
                                t && !e.confirm("작업이 진행되지 않은 이미지는 저장되지 않습니다.\n계속 하시겠습니까?") && (a = !1), a && (this.EventBus.on("AnnotationToolDatasetSaveEvent", this.fnGetDatasetSaveInfo), this.EventBus.emit("DatasetSavePopupOpen", {}))
                            } else e.alert("완료되지 않은 라벨이 있습니다.")
                        }
                    },
                    fnOpenFileDialog: function() {
                        _()("#imageFiles").click()
                    },
                    fnSaveDataset: function() {
                        var e = this;
                        e.fnInitScale(), e.saveLoading = !0;
                        var t = new x.a,
                            a = "".concat(e.datasetInfo.fileName, ".zip");
                        e.imageList.forEach((function(t) {
                            var a = e._.filter(e.imageList, (function(e) {
                                return e.newFileName + "." + e.fileExtension === t.newFileName + "." + t.fileExtension
                            }));
                            if (a.length > 1) {
                                var n = 1;
                                a.forEach((function(e) {
                                    t.seq !== e.seq && (e.newFileName = e.newFileName + "_" + n, n++)
                                }))
                            }
                        }));
                        var n = {
                                images: [],
                                annotations: [],
                                cropLabels: []
                            },
                            i = 0,
                            s = 0;
                        n.info = {
                            name: this.datasetInfo.name,
                            description: "",
                            date_created: e.$moment(new Date).format("YYYY-MM-DD HH:mm:ss")
                        }, e.imageList.some((function(a) {
                            if (!a.labelList || a.labelList.length < 1) return !1;
                            var o = e._.filter(a.labelList, (function(e) {
                                return "S" === e.type
                            }));
                            o && o.length > 0 && t.file("".concat(a.newFileName, ".").concat(a.fileExtension), a.file), o && o.length > 0 && (i++, n.images.push({
                                id: i,
                                width: a.width,
                                height: a.height,
                                file_name: a.newFileName + "." + a.fileExtension,
                                date_captured: e.$moment(a.file.lastModifiedDate).format("YYYY-MM-DD HH:mm:ss")
                            }));
                            var l = e.fnGetRatio(a);
                            a.labelList.forEach((function(t) {
                                "S" === t.type && t.boundingBoxes.forEach((function(o) {
                                    var r = a.biggerThanContainer ? Math.round(o.startX * l) : Math.round(o.startX / l),
                                        c = a.biggerThanContainer ? Math.round(o.startY * l) : Math.round(o.startY / l),
                                        d = a.biggerThanContainer ? Math.round(o.width * l) : Math.round(o.width / l),
                                        g = a.biggerThanContainer ? Math.round(o.height * l) : Math.round(o.height / l);
                                    r = Math.round(r * e.windowWidthRatio), c = Math.round(c * e.windowHeightRatio), d = Math.round(d * e.windowWidthRatio), g = Math.round(g * e.windowHeightRatio), s++, n.annotations.push({
                                        id: s,
                                        image_id: i,
                                        text: t.text,
                                        bbox: [r, c, d, g]
                                    })
                                }))
                            }));
                            var r = e._.filter(a.labelList, (function(e) {
                                    return "C" === e.type
                                })),
                                c = 0;
                            r.forEach((function(o) {
                                var r = a.biggerThanContainer ? Math.round(o.boundingBox.startX * l) : Math.round(o.boundingBox.startX / l),
                                    d = a.biggerThanContainer ? Math.round(o.boundingBox.startY * l) : Math.round(o.boundingBox.startY / l),
                                    g = a.biggerThanContainer ? Math.round(o.boundingBox.width * l) : Math.round(o.boundingBox.width / l),
                                    m = a.biggerThanContainer ? Math.round(o.boundingBox.height * l) : Math.round(o.boundingBox.height / l);
                                r = Math.round(r * e.windowWidthRatio), d = Math.round(d * e.windowHeightRatio), g = Math.round(g * e.windowWidthRatio), m = Math.round(m * e.windowHeightRatio), c++;
                                var u = "".concat(a.newFileName, "_cropped_").concat(c, ".").concat(a.fileExtension);
                                t.file(u, y(a.file, {
                                    x: r,
                                    y: d,
                                    width: g,
                                    height: m
                                }), {
                                    base64: !0
                                }), i++, n.images.push({
                                    id: i,
                                    width: Math.abs(g),
                                    height: Math.abs(m),
                                    file_name: u,
                                    date_captured: e.$moment(new Date).format("YYYY-MM-DD HH:mm:ss")
                                }), s++, n.annotations.push({
                                    id: s,
                                    image_id: i,
                                    text: o.text,
                                    bbox: [0, 0, Math.abs(g), Math.abs(m)]
                                })
                            }))
                        })), t.file("".concat(e.datasetInfo.name, "_info.json"), JSON.stringify(n, void 0, 4)), t.generateAsync({
                            type: "blob"
                        }).then((function(t) {
                            L.a.saveAs(t, a), e.statusSaved = !0, e.statusText = "데이터셋이 저장되었습니다.", e.saveLoading = !1, e.alert("데이터셋이 저장되었습니다."), window.location.reload(!0)
                        }))
                    },
                    fnLeaveBbox: function(e, t) {
                        var a = this;
                        a.selectedImage.labelList[e].boundingBoxes[t].selected = !1, a.fnDrawRectangle()
                    },
                    fnHoverBbox: function(e, t) {
                        var a = this;
                        a.selectedImage.labelList[e].boundingBoxes.forEach((function(e) {
                            e.selected = !1
                        })), a.selectedImage.labelList[e].boundingBoxes[t].selected = !0, a.fnDrawRectangle()
                    },
                    fnDeleteBbox: function(e, t) {
                        var a = this;
                        a.selectedImage.labelList[e].boundingBoxes.splice(t, 1), a.fnDrawRectangle()
                    },
                    fnAddLabel: function(e) {
                        var t = this,
                            a = !0;
                        t.selectedImage.labelList && t.selectedImage.labelList.length > 0 && t.selectedImage.labelList.forEach((function(e) {
                            e.selected && (a = !1)
                        })), a ? (t.selectedLabel = "S" === e ? t.deepCopy(t.newLabelSelectType) : t.deepCopy(t.newLabelCropType), (!t.selectedImage.labelList || t.selectedImage.labelList.length < 1) && (t.selectedImage.labelList = []), t.selectedImage.labelList.push(t.selectedLabel), t.statusSaved = !1, t.statusText = "라벨이 추가되었습니다.", t.$forceUpdate(), t.$nextTick((function() {
                            var e = t.selectedImage.labelList.length - 1;
                            _()("#labelText" + e).focus(), _()(".label_tool_list").slideUp(100)
                        }))) : t.alert("완료되지 않은 라벨이 있습니다.")
                    },
                    fnDeleteLabel: function(e) {
                        var t = this;
                        t.selectedImage.labelList.splice(e, 1), t.$forceUpdate(), t.fnDrawRectangle()
                    },
                    fnSelectLabel: function(e) {
                        var t = this;
                        t.selectedImage.labelList.forEach((function(e) {
                            e.selected = !1, "S" === e.type ? e.boundingBoxes.forEach((function(e) {
                                e.selected = !1
                            })) : e.boundingBox.selected = !1
                        })), t.selectedImage.labelList[e].selected = !0, t.selectedLabel = t.selectedImage.labelList[e], "C" === t.selectedLabel.type && (t.selectedLabel.boundingBox.selected = !0), t.$nextTick((function() {
                            t.fnDrawRectangle()
                        }))
                    },
                    fnCompleteLabel: function(e) {
                        var t = this;
                        if (!t.selectedImage.labelList[e].text || t.selectedImage.labelList[e].text.length < 1) t.alert("텍스트 입력 후 완료가 가능합니다.");
                        else {
                            if ("S" === t.selectedImage.labelList[e].type) {
                                if (!t.selectedImage.labelList[e].boundingBoxes || t.selectedImage.labelList[e].boundingBoxes.length < 1) return void t.alert("하나 이상의 바운딩박스 지정 후 완료가 가능합니다.");
                                t.selectedImage.labelList[e].selected = !1, t.selectedImage.labelList[e].boundingBoxes.forEach((function(e) {
                                    e.selected = !1
                                }))
                            } else {
                                if (!t.selectedImage.labelList[e].boundingBox.width) return void t.alert("하나 이상의 바운딩박스 지정 후 완료가 가능합니다.");
                                t.selectedImage.labelList[e].selected = !1, t.selectedImage.labelList[e].boundingBox.selected = !1
                            }
                            t.selectedLabel = t.deepCopy({}), t.selectedBbox = t.deepCopy({}), t.statusSaved = !0, t.statusText = "라벨정보가 저장되었습니다.", t.$forceUpdate(), t.$nextTick((function() {
                                t.fnDrawRectangle()
                            }))
                        }
                    },
                    fnChangeModeMove: function() {
                        var e = this;
                        if (e.zoomCount < 1) return e.isMoveMode = !1, void _()("#labelCanvas" + e.selectedImageIndex).css("cursor", "default");
                        e.isMoveMode = !e.isMoveMode, e.$forceUpdate(), e.$nextTick((function() {
                            e.isMoveMode ? _()("#labelCanvas" + e.selectedImageIndex).css("cursor", "-webkit-grab") : _()("#labelCanvas" + e.selectedImageIndex).css("cursor", "default")
                        }))
                    },
                    fnZoomIn: function() {
                        var e = this;
                        Object.keys(e.selectedImage).length < 1 || Math.abs(e.zoomCount) > 4 || (e.zoomCount++, e.scaleFactor = Math.pow(e.canvasScaleFactor, e.zoomInExp), e.selectedImage.context.clearRect(0, 0, e.selectedImage.width, e.selectedImage.height), e.selectedImage.context.scale(e.scaleFactor, e.scaleFactor), e.fnDrawRectangle())
                    },
                    fnZoomOut: function() {
                        var e = this;
                        Object.keys(e.selectedImage).length < 1 || 0 !== e.zoomCount && (e.zoomCount--, e.scaleFactor = Math.pow(e.canvasScaleFactor, e.zoomOutExp), e.selectedImage.context.clearRect(0, 0, e.selectedImage.width, e.selectedImage.height), e.selectedImage.context.scale(e.scaleFactor, e.scaleFactor), 0 === e.zoomCount && (e.imageMoveX = 0, e.imageMoveY = 0, e.selectedImage.context.setTransform(1, 0, 0, 1, e.imageMoveX, e.imageMoveY), e.fnChangeModeMove()), e.fnDrawRectangle())
                    },
                    fnInitScale: function() {
                        var e = this;
                        if (0 !== e.zoomCount) {
                            var t = e.zoomCount > 0;
                            e.imageMoveX = 0, e.imageMoveY = 0;
                            for (var a = 0; a < Math.abs(e.zoomCount); a++) e.scaleFactor = Math.pow(e.canvasScaleFactor, t ? e.zoomOutExp : e.zoomInExp), e.selectedImage.context.scale(e.scaleFactor, e.scaleFactor);
                            e.selectedImage.context.setTransform(1, 0, 0, 1, e.imageMoveX, e.imageMoveY), e.selectedImage.context.clearRect(0, 0, e.selectedImage.width, e.selectedImage.height), e.zoomCount = 0, e.fnChangeModeMove(), e.fnDrawRectangle()
                        }
                    },
                    fnSetCanvasEvent: function() {
                        var e = this;
                        e.selectedImage.canvas.addEventListener("mousedown", e.fnMouseDown, !1), e.selectedImage.canvas.addEventListener("mousemove", e.fnMouseMove, !1), e.selectedImage.canvas.addEventListener("mouseup", e.fnMouseUp, !1)
                    },
                    fnMouseDown: function() {
                        var e = this;
                        if (e.mouseIsDown = !0, e.isMoveMode) _()("#labelCanvas" + e.selectedImageIndex).css("cursor", "-webkit-grabbing");
                        else {
                            if (Object.keys(e.selectedLabel).length < 1) return;
                            e.selectedBbox = {
                                selected: !0
                            }, "S" === e.selectedLabel.type ? e.selectedLabel.boundingBoxes.push(e.selectedBbox) : e.selectedLabel.boundingBox = e.selectedBbox, e.selectedImage.context.lineWidth = 2, e.selectedImage.context.strokeStyle = "#f33";
                            var t = e.selectedImage.canvas.getBoundingClientRect();
                            if (e.startX = e.endX = event.clientX - t.left, e.startY = e.endY = event.clientY - t.top, e.startX = e.endX = e.endX + -1 * e.imageMoveX, e.startY = e.endY = e.endY + -1 * e.imageMoveY, 0 !== e.zoomCount) {
                                var a = 0,
                                    n = e.zoomCount > 0;
                                a = Math.pow(e.canvasScaleFactor, n ? e.zoomOutExp : e.zoomInExp);
                                for (var i = 0; i < Math.abs(e.zoomCount); i++) e.startX = e.endX = e.endX * a, e.startY = e.endY = e.endY * a
                            }
                            e.fnDrawRectangle(), e.canvasInterval = setInterval((function() {
                                e.fnDrawRectangle()
                            }), 5)
                        }
                    },
                    fnMouseMove: function(e) {
                        var t = this,
                            a = t.zoomCount > 0;
                        if (t.mouseIsDown)
                            if (t.isMoveMode) {
                                var n = 0;
                                n = Math.pow(t.canvasScaleFactor, a ? t.zoomInExp * t.zoomCount : t.zoomOutExp * Math.abs(t.zoomCount)), t.imageMoveX += e.movementX, t.imageMoveY += e.movementY, e.movementX > 0 && e.movementY > 0 && t.imageMoveX > 0 && t.imageMoveY > 0 && (t.imageMoveX = 0, t.imageMoveY = 0), e.movementX > 0 && t.imageMoveX > 0 && (t.imageMoveX = 0), e.movementY > 0 && t.imageMoveY > 0 && (t.imageMoveY = 0);
                                var i = 0,
                                    s = 0;
                                i = Math.round(t.imageMoveX / (-1 * (n - 1))), s = Math.round(t.imageMoveY / (-1 * (n - 1))), t.imageMoveX < 0 && t.imageMoveY < 0 && i > t.selectedImage.canvas.width && s > t.selectedImage.canvas.height && (t.imageMoveX = Math.round(t.selectedImage.canvas.width * (-1 * (n - 1))), t.imageMoveY = Math.round(t.selectedImage.canvas.height * (-1 * (n - 1)))), i > t.selectedImage.canvas.width && (t.imageMoveX = Math.round(t.selectedImage.canvas.width * (-1 * (n - 1)))), s > t.selectedImage.canvas.height && (t.imageMoveY = Math.round(t.selectedImage.canvas.height * (-1 * (n - 1)))), t.selectedImage.context.clearRect(0, 0, t.selectedImage.width, t.selectedImage.height), t.selectedImage.context.setTransform(n, 0, 0, n, t.imageMoveX, t.imageMoveY), t.fnDrawRectangle()
                            } else {
                                if (Object.keys(t.selectedLabel).length < 1) return;
                                var o = t.selectedImage.canvas.getBoundingClientRect();
                                if (t.endX = e.clientX - o.left, t.endY = e.clientY - o.top, t.endX = t.endX + -1 * t.imageMoveX, t.endY = t.endY + -1 * t.imageMoveY, 0 !== t.zoomCount) {
                                    var l = 0;
                                    l = Math.pow(t.canvasScaleFactor, a ? t.zoomOutExp : t.zoomInExp);
                                    for (var r = 0; r < Math.abs(t.zoomCount); r++) t.endX = t.endX * l, t.endY = t.endY * l
                                }
                            }
                    },
                    fnMouseUp: function() {
                        var e = this;
                        if (e.mouseIsDown)
                            if (e.mouseIsDown = !1, e.isMoveMode) _()("#labelCanvas" + e.selectedImageIndex).css("cursor", "-webkit-grab");
                            else {
                                if (e.startX > e.endX) {
                                    var t = e.startX;
                                    e.startX = e.endX, e.endX = t
                                }
                                if (e.startY > e.endY) {
                                    var a = e.startY;
                                    e.startY = e.endY, e.endY = a
                                }
                                var n = e.endX - e.startX,
                                    i = e.endY - e.startY;
                                if (n < 5 || i < 5) return void("S" === e.selectedLabel.type && e.selectedLabel.boundingBoxes.splice(e.selectedLabel.boundingBoxes.length - 1, 1));
                                "S" === e.selectedLabel.type ? e.selectedLabel.boundingBoxes[e.selectedLabel.boundingBoxes.length - 1] = {
                                    startX: e.startX,
                                    startY: e.startY,
                                    endX: e.endX,
                                    endY: e.endY,
                                    width: e.endX - e.startX,
                                    height: e.endY - e.startY,
                                    selected: !1
                                } : (e.selectedBbox.selected = !1, e.selectedLabel.boundingBox = {
                                    startX: e.startX,
                                    startY: e.startY,
                                    endX: e.endX,
                                    endY: e.endY,
                                    width: e.endX - e.startX,
                                    height: e.endY - e.startY,
                                    selected: !1
                                }), e.$forceUpdate(), e.fnDrawRectangle(), clearInterval(e.canvasInterval)
                            }
                    },
                    fnDrawRectangle: function() {
                        var e = this;
                        if (e.selectedImage.context.drawImage(e.image, 0, 0, e.image.width, e.image.height, 0, 0, e.selectedImage.canvas.width, e.selectedImage.canvas.height), e.selectedImage.labelList && e.selectedImage.labelList.length > 0 && e.selectedImage.labelList.forEach((function(t) {
                                if ("S" === t.type) t.boundingBoxes.forEach((function(t) {
                                    t.selected ? e.selectedImage.context.strokeStyle = "#f33" : e.selectedImage.context.strokeStyle = "#111", e.selectedImage.context.setLineDash([]), e.selectedImage.context.beginPath(), e.selectedImage.context.rect(t.startX * e.windowWidthRatio, t.startY * e.windowHeightRatio, t.width * e.windowWidthRatio, t.height * e.windowHeightRatio), e.selectedImage.context.closePath(), e.selectedImage.context.stroke()
                                }));
                                else {
                                    var a = t.boundingBox;
                                    a.selected ? e.selectedImage.context.strokeStyle = "#f33" : e.selectedImage.context.strokeStyle = "#111", e.selectedImage.context.globalAlpha = .1, e.selectedImage.context.fillRect(a.startX * e.windowWidthRatio, a.startY * e.windowHeightRatio, a.width * e.windowWidthRatio, a.height * e.windowHeightRatio), e.selectedImage.context.globalAlpha = 1, e.selectedImage.context.setLineDash([5, 3]), e.selectedImage.context.beginPath(), e.selectedImage.context.rect(a.startX * e.windowWidthRatio, a.startY * e.windowHeightRatio, a.width * e.windowWidthRatio, a.height * e.windowHeightRatio), e.selectedImage.context.closePath(), e.selectedImage.context.stroke()
                                }
                            })), e.mouseIsDown && !e.isMoveMode) {
                            e.selectedImage.context.strokeStyle = "#f33";
                            var t = e.endX - e.startX,
                                a = e.endY - e.startY;
                            "C" === e.selectedLabel.type ? (e.selectedImage.context.globalAlpha = .1, e.selectedImage.context.fillRect(e.startX, e.startY, t, a), e.selectedImage.context.globalAlpha = 1, e.selectedImage.context.setLineDash([5, 3])) : e.selectedImage.context.setLineDash([]), e.selectedImage.context.beginPath(), e.selectedImage.context.rect(e.startX, e.startY, t, a), e.selectedImage.context.stroke()
                        }
                    },
                    fnGetRatio: function(e) {
                        var t = _()("#canvasContainer").width(),
                            a = _()("#canvasContainer").height();
                        if (t < e.realImage.naturalWidth && a < e.realImage.naturalHeight) {
                            if (e.realImage.naturalWidth > e.realImage.naturalHeight) return parseFloat(e.realImage.naturalWidth) / parseFloat(t);
                            if (e.realImage.naturalHeight > e.realImage.naturalWidth) return parseFloat(e.realImage.naturalHeight) / parseFloat(a)
                        } else {
                            if (t < e.realImage.naturalWidth) return parseFloat(e.realImage.naturalWidth) / parseFloat(t);
                            if (a < e.realImage.naturalHeight) return parseFloat(e.realImage.naturalHeight) / parseFloat(a)
                        }
                        if (t > e.realImage.naturalWidth || a > e.realImage.naturalHeight) {
                            var n = e.realImage.naturalWidth,
                                i = e.realImage.naturalHeight,
                                s = 0;
                            while (n < t - 10 && i < a - 10) s++, n *= 1.01, i *= 1.01;
                            var o = 1.01;
                            s -= 1;
                            for (var l = 0; l < s; l++) o *= 1.01;
                            return o
                        }
                    },
                    fnSetCanvasSize: function() {
                        var e = this,
                            t = _()("#canvasContainer").width(),
                            a = _()("#canvasContainer").height(),
                            n = 0;
                        if (t < e.image.naturalWidth && a < e.image.naturalHeight) {
                            if (e.selectedImage.biggerThanContainer = !0, e.image.naturalWidth > e.image.naturalHeight) return n = parseFloat(t) / parseFloat(e.image.naturalWidth), e.selectedImage.canvas.width = Math.round(e.image.naturalWidth * n) - 5, void(e.selectedImage.canvas.height = Math.round(e.image.naturalHeight * n) - 5);
                            if (e.image.naturalHeight > e.image.naturalWidth) return n = parseFloat(a) / parseFloat(e.image.naturalHeight), e.selectedImage.canvas.width = Math.round(e.image.naturalWidth * n) - 5, void(e.selectedImage.canvas.height = Math.round(e.image.naturalHeight * n) - 5)
                        } else {
                            if (t < e.image.naturalWidth) return e.selectedImage.biggerThanContainer = !0, n = parseFloat(t) / parseFloat(e.image.naturalWidth), e.selectedImage.canvas.width = Math.round(e.image.naturalWidth * n) - 5, void(e.selectedImage.canvas.height = Math.round(e.image.naturalHeight * n) - 5);
                            if (a < e.image.naturalHeight) return e.selectedImage.biggerThanContainer = !0, n = parseFloat(a) / parseFloat(e.image.naturalHeight), e.selectedImage.canvas.width = Math.round(e.image.naturalWidth * n) - 5, void(e.selectedImage.canvas.height = Math.round(e.image.naturalHeight * n) - 5)
                        }
                        if (t > e.image.naturalWidth || a > e.image.naturalHeight) {
                            e.selectedImage.biggerThanContainer = !1;
                            var i = e.image.naturalWidth,
                                s = e.image.naturalHeight;
                            while (i < t - 10 && s < a - 10) i *= 1.01, s *= 1.01;
                            e.selectedImage.canvas.width = i, e.selectedImage.canvas.height = s
                        }
                    },
                    fnSelectImage: function(e) {
                        var t = this;
                        t.isAllLabelCompleted ? (t.selectedImage.canvas && t.fnInitScale(), t.imageList.forEach((function(e) {
                            e.selected = !1, e.labelList && e.labelList.length > 0 && e.labelList.forEach((function(e) {
                                e.selected = !1
                            }))
                        })), t.imageList[e].selected = !0, t.selectedImageIndex = e, t.selectedImage = t.imageList[e], t.statusSaved = !1, t.statusText = "이미지가 선택되었습니다. 라벨을 추가해주세요.", t.$forceUpdate(), t.selectedImage.canvas ? _()(t.selectedImage.canvas).off() : (t.selectedImage.canvas = _()("#labelCanvas" + e)[0], t.selectedImage.context = t.selectedImage.canvas.getContext("2d")), t.image = new Image, t.image.onload = function() {
                            t.selectedImage.canvas.width = t.image.naturalWidth + 1, t.selectedImage.canvas.height = t.image.naturalHeight + 1, t.fnSetCanvasSize(), t.selectedImage.realImage = t.image, t.initCanvasWidth = t.selectedImage.canvas.width, t.initCanvasHeight = t.selectedImage.canvas.height, t.selectedImage.context.drawImage(t.image, 0, 0, t.image.width, t.image.height, 0, 0, t.selectedImage.canvas.width, t.selectedImage.canvas.height), t.selectedImage.width = t.image.naturalWidth, t.selectedImage.height = t.image.naturalHeight, t.fnSetCanvasEvent(), t.imageLoading = !1, t.fnDrawRectangle()
                        }, t.imageLoading = !0, t.$forceUpdate(), t.image.src = t.selectedImage.imageUrl) : t.alert("완료되지 않은 라벨이 있습니다.")
                    },
                    fnLoadImage: function() {
                        _()("#imageFiles").click()
                    },
                    fnImageLoaded: function(e) {
                        var t = this;
                        if (e.target.files.length + t.imageList.length > 10) t.alert("이미지는 10개 이하로만 등록 가능합니다.");
                        else {
                            for (var a, n, i, s = t.imageList.length - 1, o = 0; o < e.target.files.length; o++) a = e.target.files[o], n = e.target.files[o].name.substring(0, e.target.files[o].name.lastIndexOf(".")), i = e.target.files[o].name.substring(e.target.files[o].name.lastIndexOf(".") + 1), t.imageList.push({
                                seq: s + 1,
                                file: a,
                                newFileName: n,
                                fileName: n,
                                path: a.path,
                                fileExtension: i,
                                selected: !1
                            });
                            t.$nextTick((function() {
                                for (var a = 0; a < e.target.files.length; a++) {
                                    var n = URL.createObjectURL(e.target.files[a]);
                                    _()("#image" + (s + 1 + a)).attr("src", n), t.imageList[s + 1 + a].imageUrl = n
                                }
                            }))
                        }
                    },
                    fnSetCanvasResize: function() {
                        var e = this;
                        if (e.selectedImage.canvas) {
                            var t = _()(e.selectedImage.canvas).width(),
                                a = _()(e.selectedImage.canvas).height();
                            if (e.initCanvasWidth < 0 && e.initCanvasHeight < 0) return;
                            e.windowWidthRatio = t / e.initCanvasWidth, e.windowHeightRatio = a / e.initCanvasHeight, e.image && (e.fnSetCanvasSize(), e.selectedImage.context.drawImage(e.image, 0, 0, e.image.width, e.image.height, 0, 0, e.selectedImage.canvas.width, e.selectedImage.canvas.height), e.fnDrawRectangle())
                        }
                    }
                },
                mounted: function() {
                    var e = this;
                    e.EventBus.on("GO_BOUNDING_BOX", (function() {
                        e.EventBus.emit("GO_BOUNDING_BOX_RESULT", !1)
                    })), e.EventBus.on("GO_VERIFICATION", (function() {
                        if (e.imageList && e.imageList.length > 0) {
                            var t = e.confirm("작업 중인 데이터가 모두 삭제됩니다. 이동 하시겠습니까?");
                            e.EventBus.emit("GO_VERIFICATION_RESULT", t)
                        } else e.EventBus.emit("GO_VERIFICATION_RESULT", !0)
                    })), e.EventBus.on("GO_CAPTURE", (function() {
                        if (e.imageList && e.imageList.length > 0) {
                            var t = e.confirm("작업 중인 데이터가 모두 삭제됩니다. 이동 하시겠습니까?");
                            e.EventBus.emit("GO_CAPTURE_RESULT", t)
                        } else e.EventBus.emit("GO_CAPTURE_RESULT", !0)
                    })), _()(".label_tool_sel").hover((function() {
                        Object.keys(e.selectedImage).length < 1 || _()(".label_tool_list").slideDown(100)
                    }), (function() {
                        _()(".label_tool_list").slideUp(100)
                    })), _()(".tool_opt_list").hover((function() {
                        Object.keys(e.selectedImage).length < 1 || _()(".tool_zoom_items").slideDown(100)
                    }), (function() {
                        _()(".tool_zoom_items").slideUp(100)
                    })), window.addEventListener("resize", (function() {
                        e.fnSetCanvasResize()
                    })), window.addEventListener("maximize", (function() {
                        e.fnSetCanvasResize()
                    })), window.addEventListener("unmaximize", (function() {
                        e.fnSetCanvasResize()
                    })), window.addEventListener("minimize", (function() {
                        e.fnSetCanvasResize()
                    })), window.addEventListener("restore", (function() {
                        e.fnSetCanvasResize()
                    }))
                }
            },
            F = O,
            B = (a("9c1c"), Object(o["a"])(F, p, b, !1, null, null, null)),
            Y = B.exports,
            X = function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    attrs: {
                        id: "content-main"
                    }
                }, [a("div", {
                    staticClass: "wrap_tool"
                }, [a("commonHeader"), a("div", {
                    staticClass: "to_headersub"
                }, [a("div", {
                    staticClass: "to_btn_wrap",
                    staticStyle: {
                        float: "left"
                    }
                }, [a("button", {
                    staticClass: "btn_to upload",
                    attrs: {
                        type: "button"
                    },
                    on: {
                        click: e.fnOpenFiles
                    }
                }, [e._v("파일 열기")]), a("button", {
                    staticClass: "btn_to",
                    attrs: {
                        type: "button"
                    },
                    on: {
                        click: e.fnSaveVerifyResult
                    }
                }, [e._v("저장")]), a("input", {
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file",
                        multiple: "",
                        id: "files",
                        accept: "image/*,application/json"
                    },
                    on: {
                        change: e.fnImageLoaded
                    }
                })]), a("div", {
                    staticClass: "to_dataset_name"
                }, [e.datasetJson.info && e.datasetJson.info.name ? a("span", [e._v("데이터셋 명 : " + e._s(e.datasetJson.info.name))]) : e._e()])]), a("div", {
                    staticClass: "inner"
                }, [a("div", {
                    staticClass: "tool_box left"
                }, [a("div", {
                    staticClass: "upload_list",
                    attrs: {
                        "data-simplebar": ""
                    }
                }, [a("ul", e._l(e.imageList, (function(t, n) {
                    return a("li", {
                        key: n,
                        on: {
                            click: function(t) {
                                return e.fnSelectImage(n)
                            }
                        }
                    }, [a("div", {
                        staticClass: "upload_thumb"
                    }, [a("div", {
                        staticClass: "thumb_img",
                        class: t.selected ? "current" : ""
                    }, [a("div", {
                        staticClass: "thumb_img_brd hover"
                    }), a("img", {
                        attrs: {
                            id: "image" + n,
                            src: t.imageUrl
                        }
                    })]), a("div", {
                        staticClass: "thumb_name"
                    }, [a("span", {
                        attrs: {
                            title: t.fileName
                        }
                    }, [e._v(e._s(t.fileName))])])])])
                })), 0)])]), a("div", {
                    staticClass: "tool_box center"
                }, [a("div", {
                    staticClass: "tool_head"
                }, [a("div", {
                    staticClass: "head_util"
                }, [a("div", {
                    staticClass: "tool_opt_list"
                }, [e._m(0), a("ul", {
                    staticClass: "tool_zoom_items"
                }, [a("li", [a("em", {
                    staticClass: "tool_option plus",
                    attrs: {
                        title: "확대"
                    },
                    on: {
                        click: e.fnZoomIn
                    }
                }), a("i", {
                    staticClass: "blind"
                }, [e._v("확대")])]), a("li", [a("em", {
                    staticClass: "tool_option minus",
                    attrs: {
                        title: "축소"
                    },
                    on: {
                        click: e.fnZoomOut
                    }
                }), a("i", {
                    staticClass: "blind"
                }, [e._v("축소")])]), a("li", [a("em", {
                    staticClass: "tool_option reset",
                    attrs: {
                        title: "원래대로"
                    },
                    on: {
                        click: e.fnInitScale
                    }
                }), a("i", {
                    staticClass: "blind"
                }, [e._v("원래대로")])])])]), a("div", {
                    staticClass: "tool_opt_move",
                    class: [!e.selectedImage.isCropped && e.zoomCount > 0 ? "active" : "", e.isMoveMode ? "on" : ""],
                    attrs: {
                        title: "이동"
                    },
                    on: {
                        click: e.fnChangeModeMove
                    }
                }, [a("i", {
                    staticClass: "blind"
                }, [e._v("이동")])])]), a("div", {
                    staticClass: "head_file"
                }, [a("span", [e._v("파일명")]), a("input", {
                    directives: [{
                        name: "model",
                        rawName: "v-model",
                        value: e.selectedImage.fileName,
                        expression: "selectedImage.fileName"
                    }],
                    staticClass: "form_file_name",
                    attrs: {
                        type: "text",
                        readonly: "",
                        name: ""
                    },
                    domProps: {
                        value: e.selectedImage.fileName
                    },
                    on: {
                        input: function(t) {
                            t.target.composing || e.$set(e.selectedImage, "fileName", t.target.value)
                        }
                    }
                }), a("em", [e._v("." + e._s(e.selectedImage.fileExtension))])])]), a("div", {
                    staticClass: "tool_body"
                }, [a("div", {
                    directives: [{
                        name: "show",
                        rawName: "v-show",
                        value: e.imageLoading || e.saveLoading,
                        expression: "imageLoading || saveLoading"
                    }],
                    staticClass: "loading_container"
                }, [a("div", {
                    staticClass: "loader"
                }), e.imageLoading ? a("p", {
                    staticStyle: {
                        "margin-top": "10px"
                    }
                }, [e._v("이미지를 불러오는 중입니다...")]) : e.saveLoading ? a("p", {
                    staticStyle: {
                        "margin-top": "10px"
                    }
                }, [e._v("데이터셋 저장 중입니다...")]) : e._e()]), e._m(1)])]), a("div", {
                    staticClass: "tool_box right verify"
                }, [a("div", {
                    staticClass: "tool_label"
                }, [e._m(2), a("div", {
                    staticClass: "veri_result"
                }, [a("div", {
                    staticClass: "result_cont"
                }, [a("textarea", {
                    directives: [{
                        name: "model",
                        rawName: "v-model",
                        value: e.selectedImage.failReason,
                        expression: "selectedImage.failReason"
                    }],
                    attrs: {
                        id: "failReason",
                        placeholder: "Fail 사유를 입력해 주세요."
                    },
                    domProps: {
                        value: e.selectedImage.failReason
                    },
                    on: {
                        input: function(t) {
                            t.target.composing || e.$set(e.selectedImage, "failReason", t.target.value)
                        }
                    }
                })]), a("div", {
                    staticClass: "result_tit"
                }, [a("button", {
                    staticClass: "btn_result",
                    attrs: {
                        type: "submit"
                    },
                    on: {
                        click: function(t) {
                            return e.fnVerify(!0)
                        }
                    }
                }, [e._v("Pass")]), a("button", {
                    staticClass: "btn_result",
                    attrs: {
                        type: "submit"
                    },
                    on: {
                        click: function(t) {
                            return e.fnVerify(!1)
                        }
                    }
                }, [e._v("Fail")])])]), a("div", {
                    staticClass: "label_cont"
                }, [a("div", {
                    staticClass: "label_list",
                    attrs: {
                        "data-simplebar": ""
                    }
                }, [a("div", {
                    staticClass: "dataset_info"
                }, [a("pre", [e._v("\t\t\t\t\t\t\t\t\t\t"), a("code", {
                    domProps: {
                        innerHTML: e._s(e.jsonString)
                    }
                }, [e._v("\n\t\t\t\t\t\t\t\t\t\t")]), e._v("\n\t\t\t\t\t\t\t\t\t")])])])])])])])], 1), e.imageLoading || e.saveLoading ? a("div", {
                    staticClass: "whole_dim"
                }) : e._e()])
            },
            D = [function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    staticClass: "option_head"
                }, [a("em", {
                    staticClass: "tool_option default",
                    attrs: {
                        title: "축소/확대 선택"
                    }
                }), a("i", {
                    staticClass: "blind"
                }, [e._v("축소/확대 선택")])])
            }, function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    staticClass: "canvas_container",
                    attrs: {
                        id: "canvasContainer"
                    }
                }, [a("canvas", {
                    attrs: {
                        id: "labelCanvas"
                    }
                })])
            }, function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    staticClass: "label_tp"
                }, [a("div", {
                    staticClass: "tool_label_tit"
                }, [e._v("라벨 정보")])])
            }],
            k = (a("7db0"), a("8a79"), a("2909")),
            z = a("e8ae"),
            T = a.n(z);

        function H(e) {
            return N.apply(this, arguments)
        }

        function N() {
            return N = Object(I["a"])(regeneratorRuntime.mark((function e(t) {
                return regeneratorRuntime.wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, W(t);
                        case 2:
                            return e.abrupt("return", e.sent);
                        case 3:
                        case "end":
                            return e.stop()
                    }
                }), e)
            }))), N.apply(this, arguments)
        }

        function W(e) {
            var t = new FileReader;
            return new Promise((function(a, n) {
                t.onerror = function() {
                    t.abort(), n(new DOMException("Loading error."))
                }, t.onload = function(e) {
                    a(e.target.result)
                }, t.readAsText(e, "utf-8")
            }))
        }
        var U = {
                name: "verify-tool",
                components: {},
                data: function() {
                    return {
                        directory: "",
                        initCanvasWidth: 0,
                        initCanvasHeight: 0,
                        windowWidthRatio: 1,
                        windowHeightRatio: 1,
                        image: null,
                        saveLoading: !1,
                        imageLoading: !1,
                        canvas: null,
                        context: null,
                        mouseIsDown: !1,
                        canvasInterval: null,
                        rectangles: [],
                        imageList: [],
                        selectedImage: {},
                        selectedImageIndex: -1,
                        labelList: [],
                        selectedLabel: {},
                        selectedBbox: {},
                        startX: 0,
                        startY: 0,
                        endX: 0,
                        endY: 0,
                        canvasScaleFactor: 1.1,
                        scaleFactor: null,
                        zoomCount: 0,
                        zoomInExp: 3,
                        zoomOutExp: -3,
                        imageMoveX: 0,
                        imageMoveY: 0,
                        isMoveMode: !1,
                        datasetJson: {},
                        jsonString: "",
                        jsonFile: void 0
                    }
                },
                methods: {
                    fnOpenFiles: function() {
                        document.getElementById("files").click()
                    },
                    fnVerify: function(e) {
                        var t = this;
                        if (!e && (!t.selectedImage.failReason || t.selectedImage.failReason.length < 1)) return t.alert("사유를 입력해주세요."), void _()("#failReason").focus();
                        e && (t.selectedImage.failReason = ""), t.selectedImage.isVerified = !0, t.selectedImage.isPassed = e, t.selectedImageIndex + 1 < t.imageList.length && t.fnSelectImage(t.selectedImageIndex + 1)
                    },
                    fnSaveVerifyResult: function() {
                        var e = this;
                        if (!e.imageList || e.imageList.length < 1) e.alert("저장할 정보가 없습니다.");
                        else if (e.imageList.length === e._.filter(e.imageList, (function(e) {
                                return e.isVerified
                            })).length) {
                            e.saveLoading = !0;
                            var t = new T.a.Workbook,
                                a = t.addWorksheet("검증 결과");
                            a.getCell("A1").value = "데이터셋 명", a.mergeCells("B1:D1"), a.getCell("D1").value = e.datasetJson.info.name, a.mergeCells("B2:D2"), a.getCell("A2").value = "JSON 파일 명", a.getCell("D2").value = e.jsonFile.substring(e.jsonFile.lastIndexOf("/") + 1), a.getRow(3).values = ["이미지 파일 명", "종류", "Pass / Fail", "Fail 사유"], a.columns = [{
                                key: "이미지 파일 명"
                            }, {
                                key: "종류"
                            }, {
                                key: "Pass / Fail"
                            }, {
                                key: "Fail 사유"
                            }];
                            var n = {
                                alignment: {
                                    horizontal: "center"
                                },
                                fill: {
                                    type: "pattern",
                                    pattern: "lightGray"
                                },
                                font: {
                                    bold: !0
                                }
                            };
                            a.getCell("A1").style = n, a.getCell("A2").style = n, a.getCell("A3").style = n, a.getCell("B3").style = n, a.getCell("C3").style = n, a.getCell("D3").style = n, e.imageList.forEach((function(e) {
                                a.addRow({
                                    "이미지 파일 명": e.fileName + "." + e.fileExtension,
                                    "종류": e.isCropped ? "Crop" : "Select",
                                    "Pass / Fail": e.isPassed ? "Pass" : "Fail",
                                    "Fail 사유": e.failReason
                                })
                            }));
                            var i = document.createElement("canvas"),
                                s = i.getContext("2d");
                            if (s) {
                                var o = [],
                                    l = {
                                        top: {
                                            style: "thin"
                                        },
                                        left: {
                                            style: "thin"
                                        },
                                        bottom: {
                                            style: "thin"
                                        },
                                        right: {
                                            style: "thin"
                                        }
                                    };
                                a.eachRow((function(e, t) {
                                    t < 0 || e.eachCell((function(e, t) {
                                        if (e.border = l, "string" === typeof e.value) {
                                            void 0 === o[t] && (o[t] = 0);
                                            var a = e.font && e.font.size ? e.font.size : 11;
                                            s.font = "".concat(a, "pt Arial");
                                            var n = s.measureText(e.value),
                                                i = n.width;
                                            o[t] = Math.max(o[t], i)
                                        }
                                    }))
                                }));
                                for (var r = 1; r <= a.columnCount; r++) {
                                    var c = a.getColumn(r),
                                        d = o[r];
                                    d && (c.width = d / 7.5 + 1)
                                }
                                t.xlsx.writeBuffer().then((function(t) {
                                    var a = new Blob([t], {
                                        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                                    });
                                    L.a.saveAs(a, "".concat(e.datasetJson.info.name, ".xlsx")), e.saveLoading = !1, e.alert("저장 되었습니다.")
                                })).catch((function(t) {
                                    e.saveLoading = !1, e.alert("저장 중 오류가 발생했습니다.\n오류 : " + t)
                                }))
                            }
                        } else e.alert("검증이 완료되지 않은 이미지가 있습니다.")
                    },
                    fnImageLoaded: function(e) {
                        var t = this;
                        t.imageList = [], t.datasetJson = {}, t.jsonString = "";
                        var a = Object(k["a"])(e.target.files),
                            n = a.filter((function(e) {
                                return e.name.endsWith(".json")
                            }));
                        if (!n || n.length < 1) t.alert("JSON 파일이 존재하지 않습니다.");
                        else if (n.length > 1) t.alert("JSON 파일은 해당 폴더에 하나만 있어야 합니다.");
                        else {
                            t.jsonFile = n[0].name, H(n[0]).then((function(e) {
                                t.jsonString = "", t.datasetJson = JSON.parse(e)
                            }));
                            var i = a.filter((function(e) {
                                return !e.name.endsWith(".json")
                            }));
                            t.fnSetImageList(i)
                        }
                    },
                    fnSetImageList: function(e) {
                        for (var t, a, n, i = this, s = 0; s < e.length; s++) t = e[s], a = e[s].name.substring(t.name.lastIndexOf(".") + 1), n = URL.createObjectURL(e[s]), i.imageList.push({
                            file: t,
                            newFileName: t.name.substring(0, t.name.lastIndexOf(".")),
                            fileName: t.name.substring(0, t.name.lastIndexOf(".")),
                            fileExtension: a,
                            imageUrl: n,
                            selected: !1,
                            isCropped: t.name.lastIndexOf("_cropped_") > -1,
                            isVerified: !1,
                            isPassed: !1,
                            failReason: ""
                        })
                    },
                    fnChangeModeMove: function() {
                        var e = this;
                        if (!e.selectedImage.isCropped) {
                            if (e.zoomCount < 1) return e.isMoveMode = !1, void _()("#labelCanvas").css("cursor", "default");
                            e.isMoveMode = !e.isMoveMode, e.$forceUpdate(), e.$nextTick((function() {
                                e.isMoveMode ? _()("#labelCanvas").css("cursor", "-webkit-grab") : _()("#labelCanvas").css("cursor", "default")
                            }))
                        }
                    },
                    fnZoomIn: function() {
                        var e = this;
                        if (!(Object.keys(e.selectedImage).length < 1) && !(Math.abs(e.zoomCount) > 4))
                            if (e.zoomCount++, e.scaleFactor = Math.pow(e.canvasScaleFactor, e.zoomInExp), e.context.clearRect(0, 0, e.selectedImage.width, e.selectedImage.height), e.selectedImage.isCropped) {
                                e.canvas.width = e.image.width * e.scaleFactor, e.image.width = e.image.width * e.scaleFactor, e.canvas.height = e.image.height * e.scaleFactor, e.image.height = e.image.height * e.scaleFactor, e.context.drawImage(e.image, 0, 0, e.canvas.width, e.canvas.height);
                                var t = _()(".tool_body").height() / 2 - e.image.height / 2;
                                _()("#canvasContainer").css("padding-top", t + "px")
                            } else e.context.scale(e.scaleFactor, e.scaleFactor), e.context.drawImage(e.image, 0, 0, e.image.width, e.image.height, 0, 0, e.canvas.width, e.canvas.height), e.fnDrawRectangle()
                    },
                    fnZoomOut: function() {
                        var e = this;
                        if (!(Object.keys(e.selectedImage).length < 1) && 0 !== e.zoomCount)
                            if (e.zoomCount--, e.scaleFactor = Math.pow(e.canvasScaleFactor, e.zoomOutExp), e.context.clearRect(0, 0, e.selectedImage.width, e.selectedImage.height), e.selectedImage.isCropped) {
                                e.canvas.width = e.image.width * e.scaleFactor, e.image.width = e.image.width * e.scaleFactor, e.canvas.height = e.image.height * e.scaleFactor, e.image.height = e.image.height * e.scaleFactor, e.context.drawImage(e.image, 0, 0, e.canvas.width, e.canvas.height);
                                var t = _()(".tool_body").height() / 2 - e.image.height / 2;
                                _()("#canvasContainer").css("padding-top", t + "px")
                            } else e.context.scale(e.scaleFactor, e.scaleFactor), 0 === e.zoomCount && (e.imageMoveX = 0, e.imageMoveY = 0, e.context.setTransform(1, 0, 0, 1, e.imageMoveX, e.imageMoveY), e.fnChangeModeMove()), e.context.drawImage(e.image, 0, 0, e.image.width, e.image.height, 0, 0, e.canvas.width, e.canvas.height), e.fnDrawRectangle()
                    },
                    fnInitScale: function() {
                        var e = this;
                        if (0 !== e.zoomCount)
                            if (e.zoomCount = 0, e.selectedImage.isCropped) {
                                e.canvas.width = e.selectedImage.realImage.naturalWidth, e.canvas.height = e.selectedImage.realImage.naturalHeight, e.initCanvasWidth = e.canvas.width, e.initCanvasHeight = e.canvas.height, e.image.width = e.canvas.width, e.image.height = e.canvas.height;
                                var t = _()(".tool_body").height() / 2 - e.image.height / 2;
                                _()("#canvasContainer").css("padding-top", t + "px"), e.context.drawImage(e.image, 0, 0, e.image.width, e.image.height, 0, 0, e.canvas.width, e.canvas.height)
                            } else {
                                var a = e.zoomCount > 0;
                                e.imageMoveX = 0, e.imageMoveY = 0;
                                for (var n = 0; n < Math.abs(e.zoomCount); n++) e.scaleFactor = Math.pow(e.canvasScaleFactor, a ? e.zoomOutExp : e.zoomInExp), e.context.scale(e.scaleFactor, e.scaleFactor);
                                e.context.setTransform(1, 0, 0, 1, e.imageMoveX, e.imageMoveY), e.context.clearRect(0, 0, e.selectedImage.width, e.selectedImage.height), e.fnChangeModeMove(), e.context.drawImage(e.image, 0, 0, e.image.width, e.image.height, 0, 0, e.canvas.width, e.canvas.height), e.fnDrawRectangle(), _()("#canvasContainer").css("padding-top", "0")
                            }
                    },
                    fnSetCanvasEvent: function() {
                        var e = this;
                        e.canvas.addEventListener("mousedown", e.fnMouseDown, !1), e.canvas.addEventListener("mousemove", e.fnMouseMove, !1), e.canvas.addEventListener("mouseup", e.fnMouseUp, !1)
                    },
                    fnMouseDown: function() {
                        var e = this;
                        e.mouseIsDown = !0, e.isMoveMode && _()("#labelCanvas").css("cursor", "-webkit-grabbing")
                    },
                    fnMouseMove: function(e) {
                        var t = this,
                            a = t.zoomCount > 0;
                        if (t.mouseIsDown && t.isMoveMode) {
                            var n = 0;
                            if (n = Math.pow(t.canvasScaleFactor, a ? t.zoomInExp * t.zoomCount : t.zoomOutExp * Math.abs(t.zoomCount)), t.imageMoveX += e.movementX, t.imageMoveY += e.movementY, e.movementX > 0 && e.movementY > 0 && t.imageMoveX > 0 && t.imageMoveY > 0) return t.imageMoveX = 0, void(t.imageMoveY = 0);
                            if (e.movementX > 0 && t.imageMoveX > 0) return void(t.imageMoveX = 0);
                            if (e.movementY > 0 && t.imageMoveY > 0) return void(t.imageMoveY = 0);
                            var i = Math.round(t.imageMoveX / (-1 * (n - 1))),
                                s = Math.round(t.imageMoveY / (-1 * (n - 1)));
                            if (t.imageMoveX < 0 && t.imageMoveY < 0 && i > t.canvas.width && s > t.canvas.height) return t.imageMoveX = Math.round(t.canvas.width * (-1 * (n - 1))), void(t.imageMoveY = Math.round(t.canvas.height * (-1 * (n - 1))));
                            if (i > t.canvas.width) return void(t.imageMoveX = Math.round(t.canvas.width * (-1 * (n - 1))));
                            if (s > t.canvas.height) return void(t.imageMoveY = Math.round(t.canvas.height * (-1 * (n - 1))));
                            t.context.clearRect(0, 0, t.selectedImage.width, t.selectedImage.height), t.context.setTransform(n, 0, 0, n, t.imageMoveX, t.imageMoveY), t.context.drawImage(t.image, 0, 0, t.image.width, t.image.height, 0, 0, t.canvas.width, t.canvas.height), t.fnDrawRectangle()
                        }
                    },
                    fnMouseUp: function() {
                        var e = this;
                        e.mouseIsDown && (e.mouseIsDown = !1, e.isMoveMode && _()("#labelCanvas").css("cursor", "-webkit-grab"))
                    },
                    fnSetCanvasSize: function() {
                        var e = this,
                            t = _()("#canvasContainer").width(),
                            a = _()("#canvasContainer").height(),
                            n = 0;
                        if (t < e.image.naturalWidth && a < e.image.naturalHeight) {
                            if (e.selectedImage.biggerThanContainer = !0, e.image.naturalWidth > e.image.naturalHeight) return n = parseFloat(t) / parseFloat(e.image.naturalWidth), e.canvas.width = Math.round(e.image.naturalWidth * n) - 5, void(e.canvas.height = Math.round(e.image.naturalHeight * n) - 5);
                            if (e.image.naturalHeight > e.image.naturalWidth) return n = parseFloat(a) / parseFloat(e.image.naturalHeight), e.canvas.width = Math.round(e.image.naturalWidth * n) - 5, void(e.canvas.height = Math.round(e.image.naturalHeight * n) - 5)
                        } else {
                            if (t < e.image.naturalWidth) return e.selectedImage.biggerThanContainer = !0, n = parseFloat(t) / parseFloat(e.image.naturalWidth), e.canvas.width = Math.round(e.image.naturalWidth * n) - 5, void(e.canvas.height = Math.round(e.image.naturalHeight * n) - 5);
                            if (a < e.image.naturalHeight) return e.selectedImage.biggerThanContainer = !0, n = parseFloat(a) / parseFloat(e.image.naturalHeight), e.canvas.width = Math.round(e.image.naturalWidth * n) - 5, void(e.canvas.height = Math.round(e.image.naturalHeight * n) - 5)
                        }
                        if (t > e.image.naturalWidth || a > e.image.naturalHeight) {
                            e.selectedImage.biggerThanContainer = !1;
                            var i = e.image.naturalWidth,
                                s = e.image.naturalHeight;
                            while (i < t - 10 && s < a - 10) i *= 1.01, s *= 1.01;
                            e.canvas.width = i, e.canvas.height = s
                        }
                    },
                    fnSelectImage: function(e) {
                        var t = this;
                        if (t.selectedImageIndex !== e) {
                            t.imageList.forEach((function(e) {
                                e.selected = !1
                            })), t.imageList[e].selected = !0, t.selectedImageIndex = e, t.selectedImage = t.imageList[e], t.selectedImage.labelList = [], t.$forceUpdate(), t.canvas && (_()(t.canvas).off(), t.canvas = void 0), t.canvas = _()("#labelCanvas")[0], t.context = t.canvas.getContext("2d"), t.image = new Image, t.image.onload = function() {
                                if (t.zoomCount = 0, t.selectedImage.realImage = t.image, t.canvas.width = t.selectedImage.realImage.naturalWidth, t.canvas.height = t.selectedImage.realImage.naturalHeight, _()("#canvasContainer").css("padding-top", "0"), t.selectedImage.isCropped) {
                                    var e = _()(".tool_body").height() / 2 - t.image.height / 2;
                                    _()("#canvasContainer").css("padding-top", e + "px")
                                } else t.fnSetCanvasSize();
                                var a = t.fnGetRatio(t.selectedImage),
                                    n = t._.find(t.datasetJson.images, (function(e) {
                                        return e.file_name === t.selectedImage.file.name
                                    })),
                                    i = "-1";
                                n && n.id && (i = n.id);
                                var s = t._.filter(t.datasetJson.annotations, (function(e) {
                                        return e.image_id === i
                                    })),
                                    o = t._.groupBy(s, "text"),
                                    l = function(e) {
                                        var n = [];
                                        o[e].forEach((function(e) {
                                            var i = t.selectedImage.biggerThanContainer ? e.bbox[0] / a : e.bbox[0] * a,
                                                s = t.selectedImage.biggerThanContainer ? e.bbox[1] / a : e.bbox[1] * a,
                                                o = t.selectedImage.biggerThanContainer ? e.bbox[2] / a : e.bbox[2] * a,
                                                l = t.selectedImage.biggerThanContainer ? e.bbox[3] / a : e.bbox[3] * a;
                                            n.push({
                                                startX: i,
                                                startY: s,
                                                width: o,
                                                height: l
                                            })
                                        })), t.selectedImage.labelList.push({
                                            type: "S",
                                            text: e,
                                            boundingBoxes: n
                                        })
                                    };
                                for (var r in o) l(r);
                                t.initCanvasWidth = t.canvas.width, t.initCanvasHeight = t.canvas.height, t.context.drawImage(t.image, 0, 0, t.image.width, t.image.height, 0, 0, t.canvas.width, t.canvas.height), t.fnSetCanvasEvent(), t.fnDrawRectangle(), t.imageLoading = !1
                            }, t.imageLoading = !0, t.$forceUpdate(), t.image.src = t.imageList[e].imageUrl;
                            var a = [];
                            t.datasetJson.images.forEach((function(e) {
                                var n = t.selectedImage.fileName + "." + t.selectedImage.fileExtension;
                                n === e.file_name && a.push(e)
                            })), t.jsonString = "<br>images: " + JSON.stringify(a, null, 4);
                            var n = [];
                            a.forEach((function(e) {
                                var a = t._.filter(t.datasetJson.annotations, (function(t) {
                                    return t.image_id === e.id
                                }));
                                a && n.push.apply(n, Object(k["a"])(a))
                            })), t.jsonString += "<br>annotations: " + JSON.stringify(n, null, 4)
                        }
                    },
                    fnDrawRectangle: function() {
                        var e = this;
                        e.selectedImage.labelList && e.selectedImage.labelList.length > 0 && e.selectedImage.labelList.forEach((function(t) {
                            if ("S" === t.type) t.boundingBoxes.forEach((function(t) {
                                t.selected ? e.context.strokeStyle = "#f33" : e.context.strokeStyle = "#111", e.context.setLineDash([]), e.context.beginPath(), e.context.rect(t.startX * e.windowWidthRatio, t.startY * e.windowHeightRatio, t.width * e.windowWidthRatio, t.height * e.windowHeightRatio), e.context.closePath(), e.context.stroke()
                            }));
                            else {
                                var a = t.boundingBox;
                                a.selected ? e.context.strokeStyle = "#f33" : e.context.strokeStyle = "#111", e.context.globalAlpha = .1, e.context.fillRect(a.startX * e.windowWidthRatio, a.startY * e.windowHeightRatio, a.width * e.windowWidthRatio, a.height * e.windowHeightRatio), e.context.globalAlpha = 1, e.context.setLineDash([5, 3]), e.context.beginPath(), e.context.rect(a.startX * e.windowWidthRatio, a.startY * e.windowHeightRatio, a.width * e.windowWidthRatio, a.height * e.windowHeightRatio), e.context.closePath(), e.context.stroke()
                            }
                        }))
                    },
                    fnSetCanvasResize: function() {
                        var e = this;
                        if (e.selectedImage.canvas) {
                            var t = _()(e.selectedImage.canvas).width(),
                                a = _()(e.selectedImage.canvas).height();
                            if (e.initCanvasWidth < 0 && e.initCanvasHeight < 0) return;
                            e.windowWidthRatio = t / e.initCanvasWidth, e.windowHeightRatio = a / e.initCanvasHeight, e.image && (e.fnSetCanvasSize(), e.selectedImage.context.drawImage(e.image, 0, 0, e.image.width, e.image.height, 0, 0, e.selectedImage.canvas.width, e.selectedImage.canvas.height), e.fnDrawRectangle())
                        }
                    },
                    fnGetRatio: function(e) {
                        var t = _()("#canvasContainer").width(),
                            a = _()("#canvasContainer").height();
                        if (t < e.realImage.naturalWidth && a < e.realImage.naturalHeight) {
                            if (e.realImage.naturalWidth > e.realImage.naturalHeight) return parseFloat(e.realImage.naturalWidth) / parseFloat(t);
                            if (e.realImage.naturalHeight > e.realImage.naturalWidth) return parseFloat(e.realImage.naturalHeight) / parseFloat(a)
                        } else {
                            if (t < e.realImage.naturalWidth) return parseFloat(e.realImage.naturalWidth) / parseFloat(t);
                            if (a < e.realImage.naturalHeight) return parseFloat(e.realImage.naturalHeight) / parseFloat(a)
                        }
                        if (t > e.realImage.naturalWidth || a > e.realImage.naturalHeight) {
                            var n = e.realImage.naturalWidth,
                                i = e.realImage.naturalHeight,
                                s = 0;
                            while (n < t - 10 && i < a - 10) s++, n *= 1.01, i *= 1.01;
                            var o = 1.01;
                            s -= 1;
                            for (var l = 0; l < s; l++) o *= 1.01;
                            return o
                        }
                    }
                },
                mounted: function() {
                    var e = this;
                    e.EventBus.on("GO_BOUNDING_BOX", (function() {
                        if (e.imageList && e.imageList.length > 0) {
                            var t = e.confirm("작업 중인 데이터가 모두 삭제됩니다. 이동 하시겠습니까?");
                            e.EventBus.emit("GO_BOUNDING_BOX_RESULT", t)
                        } else e.EventBus.emit("GO_BOUNDING_BOX_RESULT", !0)
                    })), e.EventBus.on("GO_VERIFICATION", (function() {
                        e.EventBus.emit("GO_VERIFICATION_RESULT", !1)
                    })), e.EventBus.on("GO_CAPTURE", (function() {
                        if (e.imageList && e.imageList.length > 0) {
                            var t = e.confirm("작업 중인 데이터가 모두 삭제됩니다. 이동 하시겠습니까?");
                            e.EventBus.emit("GO_CAPTURE_RESULT", t)
                        } else e.EventBus.emit("GO_CAPTURE_RESULT", !0)
                    })), _()(".label_tool_sel").hover((function() {
                        Object.keys(e.selectedImage).length < 1 || _()(".label_tool_list").slideDown(100)
                    }), (function() {
                        _()(".label_tool_list").slideUp(100)
                    })), _()(".tool_opt_list").hover((function() {
                        Object.keys(e.selectedImage).length < 1 || _()(".tool_zoom_items").slideDown(100)
                    }), (function() {
                        _()(".tool_zoom_items").slideUp(100)
                    })), window.addEventListener("resize", (function() {
                        e.fnSetCanvasResize()
                    })), window.addEventListener("maximize", (function() {
                        e.fnSetCanvasResize()
                    })), window.addEventListener("unmaximize", (function() {
                        e.fnSetCanvasResize()
                    })), window.addEventListener("minimize", (function() {
                        e.fnSetCanvasResize()
                    })), window.addEventListener("restore", (function() {
                        e.fnSetCanvasResize()
                    }))
                }
            },
            j = U,
            P = (a("1be7"), Object(o["a"])(j, X, D, !1, null, "2f2905c0", null)),
            $ = P.exports;
        n["a"].use(d["a"]);
        var A = [{
                path: "/",
                name: "tool-index",
                component: f
            }, {
                path: "/tool",
                name: "annotation-tool",
                component: Y
            }, {
                path: "/verify",
                name: "verify-tool",
                component: $
            }, {
                path: "*",
                redirect: "/"
            }],
            G = new d["a"]({
                mode: "history",
                base: "/",
                routes: A
            }),
            V = G,
            J = a("2f62"),
            Z = a("2a74");
        n["a"].use(J["a"]);
        var q = new J["a"].Store({
                state: {},
                mutations: {},
                actions: {},
                modules: Z["default"],
                strict: !1
            }),
            K = a("2ead"),
            Q = a.n(K),
            ee = a("9955"),
            te = a.n(ee),
            ae = a("2ef0"),
            ne = a.n(ae),
            ie = a("bc3a"),
            se = a.n(ie),
            oe = a("ecee"),
            le = a("ad3d"),
            re = a("c074"),
            ce = function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    staticClass: "ts_header"
                }, [e._m(0), a("div", {
                    staticClass: "to_menu"
                }, [a("ul", [a("li", [a("a", {
                    staticClass: "lk_obj",
                    attrs: {
                        href: "javascript:;",
                        title: "Bounding Box"
                    },
                    on: {
                        click: e.fnGoBoundingBox
                    }
                }, [a("i"), e._v("Bounding Box ")])]), a("li", [a("a", {
                    staticClass: "lk_veri",
                    attrs: {
                        href: "javascript:;",
                        title: "Verification"
                    },
                    on: {
                        click: e.fnGoVerification
                    }
                }, [a("i"), e._v("Verification ")])])])])])
            },
            de = [function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    staticClass: "ts_h1",
                    staticStyle: {
                        "margin-top": "0px",
                        position: "absolute",
                        left: "7px"
                    }
                }, [a("h1", [a("a", {
                    attrs: {
                        href: "javascript:;"
                    }
                }, [e._v("Ko.AI OCR")])])])
            }],
            ge = {
                name: "Header",
                computed: {
                    isIndexView: function() {
                        return "tool-index" === this.$route.name
                    }
                },
                methods: {
                    fnGoBoundingBox: function() {
                        var e = this;
                        this.isIndexView && this.$router.push("/tool"), this.EventBus.on("GO_BOUNDING_BOX_RESULT", (function(t) {
                            t && e.$router.push("/tool")
                        })), this.EventBus.emit("GO_BOUNDING_BOX")
                    },
                    fnGoVerification: function() {
                        var e = this;
                        this.isIndexView && this.$router.push("/verify"), this.EventBus.on("GO_VERIFICATION_RESULT", (function(t) {
                            t && e.$router.push("/verify")
                        })), this.EventBus.emit("GO_VERIFICATION")
                    },
                    fnGoCapture: function() {
                        var e = this;
                        this.isIndexView && this.$router.push("/capture"), this.EventBus.on("GO_CAPTURE_RESULT", (function(t) {
                            t && e.$router.push("/capture")
                        })), this.EventBus.emit("GO_CAPTURE")
                    }
                },
                mounted: function() {}
            },
            me = ge,
            ue = Object(o["a"])(me, ce, de, !1, null, null, null),
            he = ue.exports,
            ve = function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    staticClass: "layerpop pop_dataset",
                    attrs: {
                        id: "layerbox"
                    }
                }, [a("div", {
                    staticClass: "layerpop_area"
                }, [e._m(0), a("div", {
                    staticClass: "pop_body"
                }, [a("ul", {
                    staticClass: "dataset_info"
                }, [a("li", [a("input", {
                    directives: [{
                        name: "model",
                        rawName: "v-model",
                        value: e.datasetSaveInfo.name,
                        expression: "datasetSaveInfo.name"
                    }],
                    attrs: {
                        type: "text",
                        id: "name",
                        placeholder: "이름"
                    },
                    domProps: {
                        value: e.datasetSaveInfo.name
                    },
                    on: {
                        input: function(t) {
                            t.target.composing || e.$set(e.datasetSaveInfo, "name", t.target.value)
                        }
                    }
                })]), a("li", [a("input", {
                    directives: [{
                        name: "model",
                        rawName: "v-model",
                        value: e.datasetSaveInfo.fileName,
                        expression: "datasetSaveInfo.fileName"
                    }],
                    attrs: {
                        type: "text",
                        id: "fileName",
                        placeholder: "파일명"
                    },
                    domProps: {
                        value: e.datasetSaveInfo.fileName
                    },
                    on: {
                        input: function(t) {
                            t.target.composing || e.$set(e.datasetSaveInfo, "fileName", t.target.value)
                        }
                    }
                })])])]), a("div", {
                    staticClass: "pop_footer"
                }, [a("a", {
                    staticClass: "btn_to_pop",
                    attrs: {
                        href: "javascript:;"
                    },
                    on: {
                        click: function(t) {
                            return t.preventDefault(), e.fnPopupSave(t)
                        }
                    }
                }, [e._v("저장")]), a("a", {
                    staticClass: "pop_close btn_to_pop",
                    attrs: {
                        href: "javascript:;",
                        id: "layerbox_close"
                    },
                    on: {
                        click: function(t) {
                            return t.preventDefault(), e.fnPopupClose(t)
                        }
                    }
                }, [e._v("닫기")])])])])
            },
            fe = [function() {
                var e = this,
                    t = e.$createElement,
                    a = e._self._c || t;
                return a("div", {
                    staticClass: "pop_title"
                }, [a("span", [e._v("데이터셋")])])
            }],
            pe = {
                name: "DatasetSavePopup",
                props: ["eventBusID"],
                data: function() {
                    return {
                        datasetSaveInfo: {
                            name: "",
                            fileName: "",
                            savePath: ""
                        }
                    }
                },
                methods: {
                    fnPopupSave: function() {
                        return this.datasetSaveInfo.name ? this.datasetSaveInfo.fileName ? (this.EventBus.emit(this.eventBusID, this.datasetSaveInfo), void this.fnPopupClose()) : (this.alert("데이터셋 파일 명을 입력해주세요."), void _()("#fileName").focus()) : (this.alert("데이터셋 이름을 입력해주세요."), void _()("#name").focus())
                    },
                    fnPopupOpen: function() {
                        _()(".layerpop").css("position", "absolute"), _()(".layerpop").css("top", (_()(window).height() - _()(".layerpop").outerHeight()) / 2 + _()(window).scrollTop()), _()(".layerpop").css("left", (_()(window).width() - _()(".layerpop").outerWidth()) / 2 + _()(window).scrollLeft()), _()("#layerbox").show(), _()("#name").focus()
                    },
                    fnPopupClose: function() {
                        _()("#layerbox").hide(), _()("#mask").hide(), this.datasetSaveInfo = {
                            name: "",
                            fileName: "",
                            savePath: ""
                        }
                    }
                },
                mounted: function() {
                    this.EventBus.on("DatasetSavePopupOpen", this.fnPopupOpen)
                }
            },
            be = pe,
            Ie = Object(o["a"])(be, ve, fe, !1, null, null, null),
            we = Ie.exports;
        a("a6f9"), a("8195"), a("b277"), a("4ee2"), a("cac4");
        n["a"].component("commonHeader", he), n["a"].component("datasetSavePopup", we), n["a"].prototype.EventBus = new n["a"]({
            methods: {
                on: function(e, t) {
                    this.$off(e).$on(e, t)
                },
                emit: function(e) {
                    for (var t = arguments.length, a = new Array(t > 1 ? t - 1 : 0), n = 1; n < t; n++) a[n - 1] = arguments[n];
                    this.$emit.apply(this, [e].concat(a))
                }
            }
        }), n["a"].prototype.alert = function(e) {
            alert(e, "Annotation Tool")
        }, n["a"].prototype.confirm = function(e) {
            return confirm(e, "Annotation Tool")
        }, n["a"].prototype.deepCopy = function(e) {
            return JSON.parse(JSON.stringify(e))
        }, oe["d"].add(re["a"]), oe["b"].watch(), n["a"].http = n["a"].prototype.$http = se.a, n["a"].component("font-awesome-icon", le["a"]), n["a"].use(Q.a), n["a"].use(te.a, {
            lodash: ne.a
        }), n["a"].config.productionTip = !1, new n["a"]({
            router: V,
            store: q,
            render: function(e) {
                return e(c)
            }
        }).$mount("#app")
    },
    "5c0b": function(e, t, a) {
        "use strict";
        a("9c0c")
    },
    6079: function(e, t, a) {},
    "9c0c": function(e, t, a) {},
    "9c1c": function(e, t, a) {
        "use strict";
        a("6079")
    },
    b277: function(e, t, a) {},
    b283: function(e, t, a) {
        "use strict";
        a.r(t);
        var n = {
                main: 0
            },
            i = {
                DECREMENT_MAIN_COUNTER: function(e) {
                    e.main--
                },
                INCREMENT_MAIN_COUNTER: function(e) {
                    e.main++
                }
            },
            s = {
                someAsyncTask: function(e) {
                    var t = e.commit;
                    t("INCREMENT_MAIN_COUNTER")
                }
            };
        t["default"] = {
            state: n,
            mutations: i,
            actions: s
        }
    },
    cac4: function(e, t, a) {},
    d307: function(e, t, a) {
        var n = {
            "./Counter.js": "b283",
            "./index.js": "2a74"
        };

        function i(e) {
            var t = s(e);
            return a(t)
        }

        function s(e) {
            if (!a.o(n, e)) {
                var t = new Error("Cannot find module '" + e + "'");
                throw t.code = "MODULE_NOT_FOUND", t
            }
            return n[e]
        }
        i.keys = function() {
            return Object.keys(n)
        }, i.resolve = s, e.exports = i, i.id = "d307"
    }
});
//# sourceMappingURL=app.6ec21aae.js.map
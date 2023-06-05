package com.cxy.livecodetemplet.Util;

import com.cxy.livecodetemplet.model.MarkdownTableModel;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.ast.ThematicBreak;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TableBlock;
import com.vladsch.flexmark.ext.tables.TableBody;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MarkDownUtil {

    private static final Logger log = Logger.getInstance(MarkDownUtil.class);

    private MarkDownUtil() {
    }

    private static class SingletonInstance {
        private static final MarkDownUtil INSTANCE = new MarkDownUtil();
    }

    public static MarkDownUtil getInstance() {
        return MarkDownUtil.SingletonInstance.INSTANCE;
    }


    public String getMarkDownGithubLightCSS() {
        return "body{background-color: #ffffff;}.markdown-body{-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%;margin:0;color:#24292f;background-color:#fff;font-family:-apple-system,BlinkMacSystemFont,\"Segoe UI\",\"Noto Sans\",Helvetica,Arial,sans-serif,\"Apple Color Emoji\",\"Segoe UI Emoji\";font-size:16px;line-height:1.5;word-wrap:break-word}.markdown-body .octicon{display:inline-block;fill:currentColor;vertical-align:text-bottom}.markdown-body h1:hover .anchor .octicon-link:before,.markdown-body h2:hover .anchor .octicon-link:before,.markdown-body h3:hover .anchor .octicon-link:before,.markdown-body h4:hover .anchor .octicon-link:before,.markdown-body h5:hover .anchor .octicon-link:before,.markdown-body h6:hover .anchor .octicon-link:before{width:16px;height:16px;content:' ';display:inline-block;background-color:currentColor;-webkit-mask-image:url(\"data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' version='1.1' aria-hidden='true'><path fill-rule='evenodd' d='M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z'></path></svg>\");mask-image:url(\"data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' version='1.1' aria-hidden='true'><path fill-rule='evenodd' d='M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z'></path></svg>\")}.markdown-body details,.markdown-body figcaption,.markdown-body figure{display:block}.markdown-body summary{display:list-item}.markdown-body [hidden]{display:none!important}.markdown-body a{background-color:transparent;color:#0969da;text-decoration:none}.markdown-body abbr[title]{border-bottom:none;text-decoration:underline dotted}.markdown-body b,.markdown-body strong{font-weight:600}.markdown-body dfn{font-style:italic}.markdown-body h1{margin:.67em 0;font-weight:600;padding-bottom:.3em;font-size:2em;border-bottom:1px solid #d7dde3}.markdown-body mark{background-color:#fff8c5;color:#24292f}.markdown-body small{font-size:90%}.markdown-body sub,.markdown-body sup{font-size:75%;line-height:0;position:relative;vertical-align:baseline}.markdown-body sub{bottom:-.25em}.markdown-body sup{top:-.5em}.markdown-body img{border-style:none;max-width:100%;box-sizing:content-box;background-color:#fff}.markdown-body code,.markdown-body kbd,.markdown-body pre,.markdown-body samp{font-family:monospace;font-size:1em}.markdown-body figure{margin:1em 40px}.markdown-body hr{box-sizing:content-box;overflow:hidden;background:0 0;border-bottom:1px solid #d7dde3;height:.25em;padding:0;margin:24px 0;background-color:#d0d7de;border:0}.markdown-body input{font:inherit;margin:0;overflow:visible;font-family:inherit;font-size:inherit;line-height:inherit}.markdown-body [type=button],.markdown-body [type=reset],.markdown-body [type=submit]{-webkit-appearance:button}.markdown-body [type=checkbox],.markdown-body [type=radio]{box-sizing:border-box;padding:0}.markdown-body [type=number]::-webkit-inner-spin-button,.markdown-body [type=number]::-webkit-outer-spin-button{height:auto}.markdown-body [type=search]::-webkit-search-cancel-button,.markdown-body [type=search]::-webkit-search-decoration{-webkit-appearance:none}.markdown-body ::-webkit-input-placeholder{color:inherit;opacity:.54}.markdown-body ::-webkit-file-upload-button{-webkit-appearance:button;font:inherit}.markdown-body a:hover{text-decoration:underline}.markdown-body ::placeholder{color:#6e7781;opacity:1}.markdown-body hr::before{display:table;content:\"\"}.markdown-body hr::after{display:table;clear:both;content:\"\"}.markdown-body table{border-spacing:0;border-collapse:collapse;display:block;width:max-content;max-width:100%;overflow:auto}.markdown-body td,.markdown-body th{padding:0}.markdown-body details summary{cursor:pointer}.markdown-body details:not([open])>:not(summary){display:none!important}.markdown-body [role=button]:focus,.markdown-body a:focus,.markdown-body input[type=checkbox]:focus,.markdown-body input[type=radio]:focus{outline:2px solid #0969da;outline-offset:-2px;box-shadow:none}.markdown-body [role=button]:focus:not(:focus-visible),.markdown-body a:focus:not(:focus-visible),.markdown-body input[type=checkbox]:focus:not(:focus-visible),.markdown-body input[type=radio]:focus:not(:focus-visible){outline:solid 1px transparent}.markdown-body [role=button]:focus-visible,.markdown-body a:focus-visible,.markdown-body input[type=checkbox]:focus-visible,.markdown-body input[type=radio]:focus-visible{outline:2px solid #0969da;outline-offset:-2px;box-shadow:none}.markdown-body a:not([class]):focus,.markdown-body a:not([class]):focus-visible,.markdown-body input[type=checkbox]:focus,.markdown-body input[type=checkbox]:focus-visible,.markdown-body input[type=radio]:focus,.markdown-body input[type=radio]:focus-visible{outline-offset:0}.markdown-body kbd{display:inline-block;padding:3px 5px;font:11px ui-monospace,SFMono-Regular,SF Mono,Menlo,Consolas,Liberation Mono,monospace;line-height:10px;color:#24292f;vertical-align:middle;background-color:#f6f8fa;border:solid 1px rgba(175,184,193,.2);border-bottom-color:rgba(175,184,193,.2);border-radius:6px;box-shadow:inset 0 -1px 0 rgba(175,184,193,.2)}.markdown-body h1,.markdown-body h2,.markdown-body h3,.markdown-body h4,.markdown-body h5,.markdown-body h6{margin-top:24px;margin-bottom:16px;font-weight:600;line-height:1.25}.markdown-body h2{font-weight:600;padding-bottom:.3em;font-size:1.5em;border-bottom:1px solid #d7dde3}.markdown-body h3{font-weight:600;font-size:1.25em}.markdown-body h4{font-weight:600;font-size:1em}.markdown-body h5{font-weight:600;font-size:.875em}.markdown-body h6{font-weight:600;font-size:.85em;color:#57606a}.markdown-body p{margin-top:0;margin-bottom:10px}.markdown-body blockquote{margin:0;padding:0 1em;color:#57606a;border-left:.25em solid #d0d7de}.markdown-body ol,.markdown-body ul{margin-top:0;margin-bottom:0;padding-left:2em}.markdown-body ol ol,.markdown-body ul ol{list-style-type:lower-roman}.markdown-body ol ol ol,.markdown-body ol ul ol,.markdown-body ul ol ol,.markdown-body ul ul ol{list-style-type:lower-alpha}.markdown-body dd{margin-left:0}.markdown-body code,.markdown-body samp,.markdown-body tt{font-family:ui-monospace,SFMono-Regular,SF Mono,Menlo,Consolas,Liberation Mono,monospace;font-size:12px}.markdown-body pre{margin-top:0;margin-bottom:0;font-family:ui-monospace,SFMono-Regular,SF Mono,Menlo,Consolas,Liberation Mono,monospace;font-size:12px;word-wrap:normal}.markdown-body .octicon{display:inline-block;overflow:visible!important;vertical-align:text-bottom;fill:currentColor}.markdown-body input::-webkit-inner-spin-button,.markdown-body input::-webkit-outer-spin-button{margin:0;-webkit-appearance:none;appearance:none}.markdown-body::before{display:table;content:\"\"}.markdown-body::after{display:table;clear:both;content:\"\"}.markdown-body>:first-child{margin-top:0!important}.markdown-body>:last-child{margin-bottom:0!important}.markdown-body a:not([href]){color:inherit;text-decoration:none}.markdown-body .absent{color:#cf222e}.markdown-body .anchor{float:left;padding-right:4px;margin-left:-20px;line-height:1}.markdown-body .anchor:focus{outline:0}.markdown-body blockquote,.markdown-body details,.markdown-body dl,.markdown-body ol,.markdown-body p,.markdown-body pre,.markdown-body table,.markdown-body ul{margin-top:0;margin-bottom:16px}.markdown-body blockquote>:first-child{margin-top:0}.markdown-body blockquote>:last-child{margin-bottom:0}.markdown-body h1 .octicon-link,.markdown-body h2 .octicon-link,.markdown-body h3 .octicon-link,.markdown-body h4 .octicon-link,.markdown-body h5 .octicon-link,.markdown-body h6 .octicon-link{color:#24292f;vertical-align:middle;visibility:hidden}.markdown-body h1:hover .anchor,.markdown-body h2:hover .anchor,.markdown-body h3:hover .anchor,.markdown-body h4:hover .anchor,.markdown-body h5:hover .anchor,.markdown-body h6:hover .anchor{text-decoration:none}.markdown-body h1:hover .anchor .octicon-link,.markdown-body h2:hover .anchor .octicon-link,.markdown-body h3:hover .anchor .octicon-link,.markdown-body h4:hover .anchor .octicon-link,.markdown-body h5:hover .anchor .octicon-link,.markdown-body h6:hover .anchor .octicon-link{visibility:visible}.markdown-body h1 code,.markdown-body h1 tt,.markdown-body h2 code,.markdown-body h2 tt,.markdown-body h3 code,.markdown-body h3 tt,.markdown-body h4 code,.markdown-body h4 tt,.markdown-body h5 code,.markdown-body h5 tt,.markdown-body h6 code,.markdown-body h6 tt{padding:0 .2em;font-size:inherit}.markdown-body summary h1,.markdown-body summary h2,.markdown-body summary h3,.markdown-body summary h4,.markdown-body summary h5,.markdown-body summary h6{display:inline-block}.markdown-body summary h1 .anchor,.markdown-body summary h2 .anchor,.markdown-body summary h3 .anchor,.markdown-body summary h4 .anchor,.markdown-body summary h5 .anchor,.markdown-body summary h6 .anchor{margin-left:-40px}.markdown-body summary h1,.markdown-body summary h2{padding-bottom:0;border-bottom:0}.markdown-body ol.no-list,.markdown-body ul.no-list{padding:0;list-style-type:none}.markdown-body ol[type=a]{list-style-type:lower-alpha}.markdown-body ol[type=A]{list-style-type:upper-alpha}.markdown-body ol[type=i]{list-style-type:lower-roman}.markdown-body ol[type=I]{list-style-type:upper-roman}.markdown-body ol[type=\"1\"]{list-style-type:decimal}.markdown-body div>ol:not([type]){list-style-type:decimal}.markdown-body ol ol,.markdown-body ol ul,.markdown-body ul ol,.markdown-body ul ul{margin-top:0;margin-bottom:0}.markdown-body li>p{margin-top:16px}.markdown-body li+li{margin-top:.25em}.markdown-body dl{padding:0}.markdown-body dl dt{padding:0;margin-top:16px;font-size:1em;font-style:italic;font-weight:600}.markdown-body dl dd{padding:0 16px;margin-bottom:16px}.markdown-body table th{font-weight:600}.markdown-body table td,.markdown-body table th{padding:6px 13px;border:1px solid #d0d7de}.markdown-body table tr{background-color:#fff;border-top:1px solid #d7dde3}.markdown-body table tr:nth-child(2n){background-color:#f6f8fa}.markdown-body table img{background-color:transparent}.markdown-body img[align=right]{padding-left:20px}.markdown-body img[align=left]{padding-right:20px}.markdown-body .emoji{max-width:none;vertical-align:text-top;background-color:transparent}.markdown-body span.frame{display:block;overflow:hidden}.markdown-body span.frame>span{display:block;float:left;width:auto;padding:7px;margin:13px 0 0;overflow:hidden;border:1px solid #d0d7de}.markdown-body span.frame span img{display:block;float:left}.markdown-body span.frame span span{display:block;padding:5px 0 0;clear:both;color:#24292f}.markdown-body span.align-center{display:block;overflow:hidden;clear:both}.markdown-body span.align-center>span{display:block;margin:13px auto 0;overflow:hidden;text-align:center}.markdown-body span.align-center span img{margin:0 auto;text-align:center}.markdown-body span.align-right{display:block;overflow:hidden;clear:both}.markdown-body span.align-right>span{display:block;margin:13px 0 0;overflow:hidden;text-align:right}.markdown-body span.align-right span img{margin:0;text-align:right}.markdown-body span.float-left{display:block;float:left;margin-right:13px;overflow:hidden}.markdown-body span.float-left span{margin:13px 0 0}.markdown-body span.float-right{display:block;float:right;margin-left:13px;overflow:hidden}.markdown-body span.float-right>span{display:block;margin:13px auto 0;overflow:hidden;text-align:right}.markdown-body code,.markdown-body tt{padding:.2em .4em;margin:0;font-size:85%;white-space:break-spaces;background-color:rgba(175,184,193,.2);border-radius:6px}.markdown-body code br,.markdown-body tt br{display:none}.markdown-body del code{text-decoration:inherit}.markdown-body samp{font-size:85%}.markdown-body pre code{font-size:100%}.markdown-body pre>code{padding:0;margin:0;word-break:normal;white-space:pre;background:0 0;border:0}.markdown-body .highlight{margin-bottom:16px}.markdown-body .highlight pre{margin-bottom:0;word-break:normal}.markdown-body .highlight pre,.markdown-body pre{padding:16px;overflow:auto;font-size:85%;line-height:1.45;background-color:#f6f8fa;border-radius:6px}.markdown-body pre code,.markdown-body pre tt{display:inline;max-width:auto;padding:0;margin:0;overflow:visible;line-height:inherit;word-wrap:normal;background-color:transparent;border:0}.markdown-body .csv-data td,.markdown-body .csv-data th{padding:5px;overflow:hidden;font-size:12px;line-height:1;text-align:left;white-space:nowrap}.markdown-body .csv-data .blob-num{padding:10px 8px 9px;text-align:right;background:#fff;border:0}.markdown-body .csv-data tr{border-top:0}.markdown-body .csv-data th{font-weight:600;background:#f6f8fa;border-top:0}.markdown-body [data-footnote-ref]::before{content:\"[\"}.markdown-body [data-footnote-ref]::after{content:\"]\"}.markdown-body .footnotes{font-size:12px;color:#57606a;border-top:1px solid #d0d7de}.markdown-body .footnotes ol{padding-left:16px}.markdown-body .footnotes ol ul{display:inline-block;padding-left:16px;margin-top:16px}.markdown-body .footnotes li{position:relative}.markdown-body .footnotes li:target::before{position:absolute;top:-8px;right:-8px;bottom:-8px;left:-24px;pointer-events:none;content:\"\";border:2px solid #0969da;border-radius:6px}.markdown-body .footnotes li:target{color:#24292f}.markdown-body .footnotes .data-footnote-backref g-emoji{font-family:monospace}.markdown-body .pl-c{color:#6e7781}.markdown-body .pl-c1,.markdown-body .pl-s .pl-v{color:#0550ae}.markdown-body .pl-e,.markdown-body .pl-en{color:#8250df}.markdown-body .pl-s .pl-s1,.markdown-body .pl-smi{color:#24292f}.markdown-body .pl-ent{color:#116329}.markdown-body .pl-k{color:#cf222e}.markdown-body .pl-pds,.markdown-body .pl-s,.markdown-body .pl-s .pl-pse .pl-s1,.markdown-body .pl-sr,.markdown-body .pl-sr .pl-cce,.markdown-body .pl-sr .pl-sra,.markdown-body .pl-sr .pl-sre{color:#0a3069}.markdown-body .pl-smw,.markdown-body .pl-v{color:#953800}.markdown-body .pl-bu{color:#82071e}.markdown-body .pl-ii{color:#f6f8fa;background-color:#82071e}.markdown-body .pl-c2{color:#f6f8fa;background-color:#cf222e}.markdown-body .pl-sr .pl-cce{font-weight:700;color:#116329}.markdown-body .pl-ml{color:#3b2300}.markdown-body .pl-mh,.markdown-body .pl-mh .pl-en,.markdown-body .pl-ms{font-weight:700;color:#0550ae}.markdown-body .pl-mi{font-style:italic;color:#24292f}.markdown-body .pl-mb{font-weight:700;color:#24292f}.markdown-body .pl-md{color:#82071e;background-color:#ffebe9}.markdown-body .pl-mi1{color:#116329;background-color:#dafbe1}.markdown-body .pl-mc{color:#953800;background-color:#ffd8b5}.markdown-body .pl-mi2{color:#eaeef2;background-color:#0550ae}.markdown-body .pl-mdr{font-weight:700;color:#8250df}.markdown-body .pl-ba{color:#57606a}.markdown-body .pl-sg{color:#8c959f}.markdown-body .pl-corl{text-decoration:underline;color:#0a3069}.markdown-body g-emoji{display:inline-block;min-width:1ch;font-family:\"Apple Color Emoji\",\"Segoe UI Emoji\",\"Segoe UI Symbol\";font-size:1em;font-style:normal!important;font-weight:400;line-height:1;vertical-align:-.075em}.markdown-body g-emoji img{width:1em;height:1em}.markdown-body .task-list-item{list-style-type:none}.markdown-body .task-list-item label{font-weight:400}.markdown-body .task-list-item.enabled label{cursor:pointer}.markdown-body .task-list-item+.task-list-item{margin-top:4px}.markdown-body .task-list-item .handle{display:none}.markdown-body .task-list-item-checkbox{margin:0 .2em .25em -1.4em;vertical-align:middle}.markdown-body .contains-task-list:dir(rtl) .task-list-item-checkbox{margin:0 -1.6em .25em .2em}.markdown-body .contains-task-list{position:relative}.markdown-body .contains-task-list:focus-within .task-list-item-convert-container,.markdown-body .contains-task-list:hover .task-list-item-convert-container{display:block;width:auto;height:24px;overflow:visible;clip:auto}.markdown-body ::-webkit-calendar-picker-indicator{filter:invert(50%)}";
    }

    public String getMarkDownGithubDarkCSS() {
        return "body{background-color:#0d1117;}.markdown-body{color-scheme:dark;-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%;margin:0;color:#c9d1d9;background-color:#0d1117;font-family:-apple-system,BlinkMacSystemFont,\"Segoe UI\",\"Noto Sans\",Helvetica,Arial,sans-serif,\"Apple Color Emoji\",\"Segoe UI Emoji\";font-size:16px;line-height:1.5;word-wrap:break-word}.markdown-body .octicon{display:inline-block;fill:currentColor;vertical-align:text-bottom}.markdown-body h1:hover .anchor .octicon-link:before,.markdown-body h2:hover .anchor .octicon-link:before,.markdown-body h3:hover .anchor .octicon-link:before,.markdown-body h4:hover .anchor .octicon-link:before,.markdown-body h5:hover .anchor .octicon-link:before,.markdown-body h6:hover .anchor .octicon-link:before{width:16px;height:16px;content:' ';display:inline-block;background-color:currentColor;-webkit-mask-image:url(\"data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' version='1.1' aria-hidden='true'><path fill-rule='evenodd' d='M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z'></path></svg>\");mask-image:url(\"data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' version='1.1' aria-hidden='true'><path fill-rule='evenodd' d='M7.775 3.275a.75.75 0 001.06 1.06l1.25-1.25a2 2 0 112.83 2.83l-2.5 2.5a2 2 0 01-2.83 0 .75.75 0 00-1.06 1.06 3.5 3.5 0 004.95 0l2.5-2.5a3.5 3.5 0 00-4.95-4.95l-1.25 1.25zm-4.69 9.64a2 2 0 010-2.83l2.5-2.5a2 2 0 012.83 0 .75.75 0 001.06-1.06 3.5 3.5 0 00-4.95 0l-2.5 2.5a3.5 3.5 0 004.95 4.95l1.25-1.25a.75.75 0 00-1.06-1.06l-1.25 1.25a2 2 0 01-2.83 0z'></path></svg>\")}.markdown-body details,.markdown-body figcaption,.markdown-body figure{display:block}.markdown-body summary{display:list-item}.markdown-body [hidden]{display:none!important}.markdown-body a{background-color:transparent;color:#58a6ff;text-decoration:none}.markdown-body abbr[title]{border-bottom:none;text-decoration:underline dotted}.markdown-body b,.markdown-body strong{font-weight:600}.markdown-body dfn{font-style:italic}.markdown-body h1{margin:.67em 0;font-weight:600;padding-bottom:.3em;font-size:2em;border-bottom:1px solid #21262d}.markdown-body mark{background-color:rgba(187,128,9,.15);color:#c9d1d9}.markdown-body small{font-size:90%}.markdown-body sub,.markdown-body sup{font-size:75%;line-height:0;position:relative;vertical-align:baseline}.markdown-body sub{bottom:-.25em}.markdown-body sup{top:-.5em}.markdown-body img{border-style:none;max-width:100%;box-sizing:content-box;background-color:#0d1117}.markdown-body code,.markdown-body kbd,.markdown-body pre,.markdown-body samp{font-family:monospace;font-size:1em}.markdown-body figure{margin:1em 40px}.markdown-body hr{box-sizing:content-box;overflow:hidden;background:0 0;border-bottom:1px solid #21262d;height:.25em;padding:0;margin:24px 0;background-color:#30363d;border:0}.markdown-body input{font:inherit;margin:0;overflow:visible;font-family:inherit;font-size:inherit;line-height:inherit}.markdown-body [type=button],.markdown-body [type=reset],.markdown-body [type=submit]{-webkit-appearance:button}.markdown-body [type=checkbox],.markdown-body [type=radio]{box-sizing:border-box;padding:0}.markdown-body [type=number]::-webkit-inner-spin-button,.markdown-body [type=number]::-webkit-outer-spin-button{height:auto}.markdown-body [type=search]::-webkit-search-cancel-button,.markdown-body [type=search]::-webkit-search-decoration{-webkit-appearance:none}.markdown-body ::-webkit-input-placeholder{color:inherit;opacity:.54}.markdown-body ::-webkit-file-upload-button{-webkit-appearance:button;font:inherit}.markdown-body a:hover{text-decoration:underline}.markdown-body ::placeholder{color:#6e7681;opacity:1}.markdown-body hr::before{display:table;content:\"\"}.markdown-body hr::after{display:table;clear:both;content:\"\"}.markdown-body table{border-spacing:0;border-collapse:collapse;display:block;width:max-content;max-width:100%;overflow:auto}.markdown-body td,.markdown-body th{padding:0}.markdown-body details summary{cursor:pointer}.markdown-body details:not([open])>:not(summary){display:none!important}.markdown-body [role=button]:focus,.markdown-body a:focus,.markdown-body input[type=checkbox]:focus,.markdown-body input[type=radio]:focus{outline:2px solid #58a6ff;outline-offset:-2px;box-shadow:none}.markdown-body [role=button]:focus:not(:focus-visible),.markdown-body a:focus:not(:focus-visible),.markdown-body input[type=checkbox]:focus:not(:focus-visible),.markdown-body input[type=radio]:focus:not(:focus-visible){outline:solid 1px transparent}.markdown-body [role=button]:focus-visible,.markdown-body a:focus-visible,.markdown-body input[type=checkbox]:focus-visible,.markdown-body input[type=radio]:focus-visible{outline:2px solid #58a6ff;outline-offset:-2px;box-shadow:none}.markdown-body a:not([class]):focus,.markdown-body a:not([class]):focus-visible,.markdown-body input[type=checkbox]:focus,.markdown-body input[type=checkbox]:focus-visible,.markdown-body input[type=radio]:focus,.markdown-body input[type=radio]:focus-visible{outline-offset:0}.markdown-body kbd{display:inline-block;padding:3px 5px;font:11px ui-monospace,SFMono-Regular,SF Mono,Menlo,Consolas,Liberation Mono,monospace;line-height:10px;color:#c9d1d9;vertical-align:middle;background-color:#161b22;border:solid 1px rgba(110,118,129,.4);border-bottom-color:rgba(110,118,129,.4);border-radius:6px;box-shadow:inset 0 -1px 0 rgba(110,118,129,.4)}.markdown-body h1,.markdown-body h2,.markdown-body h3,.markdown-body h4,.markdown-body h5,.markdown-body h6{margin-top:24px;margin-bottom:16px;font-weight:600;line-height:1.25}.markdown-body h2{font-weight:600;padding-bottom:.3em;font-size:1.5em;border-bottom:1px solid #21262d}.markdown-body h3{font-weight:600;font-size:1.25em}.markdown-body h4{font-weight:600;font-size:1em}.markdown-body h5{font-weight:600;font-size:.875em}.markdown-body h6{font-weight:600;font-size:.85em;color:#8b949e}.markdown-body p{margin-top:0;margin-bottom:10px}.markdown-body blockquote{margin:0;padding:0 1em;color:#8b949e;border-left:.25em solid #30363d}.markdown-body ol,.markdown-body ul{margin-top:0;margin-bottom:0;padding-left:2em}.markdown-body ol ol,.markdown-body ul ol{list-style-type:lower-roman}.markdown-body ol ol ol,.markdown-body ol ul ol,.markdown-body ul ol ol,.markdown-body ul ul ol{list-style-type:lower-alpha}.markdown-body dd{margin-left:0}.markdown-body code,.markdown-body samp,.markdown-body tt{font-family:ui-monospace,SFMono-Regular,SF Mono,Menlo,Consolas,Liberation Mono,monospace;font-size:12px}.markdown-body pre{margin-top:0;margin-bottom:0;font-family:ui-monospace,SFMono-Regular,SF Mono,Menlo,Consolas,Liberation Mono,monospace;font-size:12px;word-wrap:normal}.markdown-body .octicon{display:inline-block;overflow:visible!important;vertical-align:text-bottom;fill:currentColor}.markdown-body input::-webkit-inner-spin-button,.markdown-body input::-webkit-outer-spin-button{margin:0;-webkit-appearance:none;appearance:none}.markdown-body::before{display:table;content:\"\"}.markdown-body::after{display:table;clear:both;content:\"\"}.markdown-body>:first-child{margin-top:0!important}.markdown-body>:last-child{margin-bottom:0!important}.markdown-body a:not([href]){color:inherit;text-decoration:none}.markdown-body .absent{color:#f85149}.markdown-body .anchor{float:left;padding-right:4px;margin-left:-20px;line-height:1}.markdown-body .anchor:focus{outline:0}.markdown-body blockquote,.markdown-body details,.markdown-body dl,.markdown-body ol,.markdown-body p,.markdown-body pre,.markdown-body table,.markdown-body ul{margin-top:0;margin-bottom:16px}.markdown-body blockquote>:first-child{margin-top:0}.markdown-body blockquote>:last-child{margin-bottom:0}.markdown-body h1 .octicon-link,.markdown-body h2 .octicon-link,.markdown-body h3 .octicon-link,.markdown-body h4 .octicon-link,.markdown-body h5 .octicon-link,.markdown-body h6 .octicon-link{color:#c9d1d9;vertical-align:middle;visibility:hidden}.markdown-body h1:hover .anchor,.markdown-body h2:hover .anchor,.markdown-body h3:hover .anchor,.markdown-body h4:hover .anchor,.markdown-body h5:hover .anchor,.markdown-body h6:hover .anchor{text-decoration:none}.markdown-body h1:hover .anchor .octicon-link,.markdown-body h2:hover .anchor .octicon-link,.markdown-body h3:hover .anchor .octicon-link,.markdown-body h4:hover .anchor .octicon-link,.markdown-body h5:hover .anchor .octicon-link,.markdown-body h6:hover .anchor .octicon-link{visibility:visible}.markdown-body h1 code,.markdown-body h1 tt,.markdown-body h2 code,.markdown-body h2 tt,.markdown-body h3 code,.markdown-body h3 tt,.markdown-body h4 code,.markdown-body h4 tt,.markdown-body h5 code,.markdown-body h5 tt,.markdown-body h6 code,.markdown-body h6 tt{padding:0 .2em;font-size:inherit}.markdown-body summary h1,.markdown-body summary h2,.markdown-body summary h3,.markdown-body summary h4,.markdown-body summary h5,.markdown-body summary h6{display:inline-block}.markdown-body summary h1 .anchor,.markdown-body summary h2 .anchor,.markdown-body summary h3 .anchor,.markdown-body summary h4 .anchor,.markdown-body summary h5 .anchor,.markdown-body summary h6 .anchor{margin-left:-40px}.markdown-body summary h1,.markdown-body summary h2{padding-bottom:0;border-bottom:0}.markdown-body ol.no-list,.markdown-body ul.no-list{padding:0;list-style-type:none}.markdown-body ol[type=a]{list-style-type:lower-alpha}.markdown-body ol[type=A]{list-style-type:upper-alpha}.markdown-body ol[type=i]{list-style-type:lower-roman}.markdown-body ol[type=I]{list-style-type:upper-roman}.markdown-body ol[type=\"1\"]{list-style-type:decimal}.markdown-body div>ol:not([type]){list-style-type:decimal}.markdown-body ol ol,.markdown-body ol ul,.markdown-body ul ol,.markdown-body ul ul{margin-top:0;margin-bottom:0}.markdown-body li>p{margin-top:16px}.markdown-body li+li{margin-top:.25em}.markdown-body dl{padding:0}.markdown-body dl dt{padding:0;margin-top:16px;font-size:1em;font-style:italic;font-weight:600}.markdown-body dl dd{padding:0 16px;margin-bottom:16px}.markdown-body table th{font-weight:600}.markdown-body table td,.markdown-body table th{padding:6px 13px;border:1px solid #30363d}.markdown-body table tr{background-color:#0d1117;border-top:1px solid #21262d}.markdown-body table tr:nth-child(2n){background-color:#161b22}.markdown-body table img{background-color:transparent}.markdown-body img[align=right]{padding-left:20px}.markdown-body img[align=left]{padding-right:20px}.markdown-body .emoji{max-width:none;vertical-align:text-top;background-color:transparent}.markdown-body span.frame{display:block;overflow:hidden}.markdown-body span.frame>span{display:block;float:left;width:auto;padding:7px;margin:13px 0 0;overflow:hidden;border:1px solid #30363d}.markdown-body span.frame span img{display:block;float:left}.markdown-body span.frame span span{display:block;padding:5px 0 0;clear:both;color:#c9d1d9}.markdown-body span.align-center{display:block;overflow:hidden;clear:both}.markdown-body span.align-center>span{display:block;margin:13px auto 0;overflow:hidden;text-align:center}.markdown-body span.align-center span img{margin:0 auto;text-align:center}.markdown-body span.align-right{display:block;overflow:hidden;clear:both}.markdown-body span.align-right>span{display:block;margin:13px 0 0;overflow:hidden;text-align:right}.markdown-body span.align-right span img{margin:0;text-align:right}.markdown-body span.float-left{display:block;float:left;margin-right:13px;overflow:hidden}.markdown-body span.float-left span{margin:13px 0 0}.markdown-body span.float-right{display:block;float:right;margin-left:13px;overflow:hidden}.markdown-body span.float-right>span{display:block;margin:13px auto 0;overflow:hidden;text-align:right}.markdown-body code,.markdown-body tt{padding:.2em .4em;margin:0;font-size:85%;white-space:break-spaces;background-color:rgba(110,118,129,.4);border-radius:6px}.markdown-body code br,.markdown-body tt br{display:none}.markdown-body del code{text-decoration:inherit}.markdown-body samp{font-size:85%}.markdown-body pre code{font-size:100%}.markdown-body pre>code{padding:0;margin:0;word-break:normal;white-space:pre;background:0 0;border:0}.markdown-body .highlight{margin-bottom:16px}.markdown-body .highlight pre{margin-bottom:0;word-break:normal}.markdown-body .highlight pre,.markdown-body pre{padding:16px;overflow:auto;font-size:85%;line-height:1.45;background-color:#161b22;border-radius:6px}.markdown-body pre code,.markdown-body pre tt{display:inline;max-width:auto;padding:0;margin:0;overflow:visible;line-height:inherit;word-wrap:normal;background-color:transparent;border:0}.markdown-body .csv-data td,.markdown-body .csv-data th{padding:5px;overflow:hidden;font-size:12px;line-height:1;text-align:left;white-space:nowrap}.markdown-body .csv-data .blob-num{padding:10px 8px 9px;text-align:right;background:#0d1117;border:0}.markdown-body .csv-data tr{border-top:0}.markdown-body .csv-data th{font-weight:600;background:#161b22;border-top:0}.markdown-body [data-footnote-ref]::before{content:\"[\"}.markdown-body [data-footnote-ref]::after{content:\"]\"}.markdown-body .footnotes{font-size:12px;color:#8b949e;border-top:1px solid #30363d}.markdown-body .footnotes ol{padding-left:16px}.markdown-body .footnotes ol ul{display:inline-block;padding-left:16px;margin-top:16px}.markdown-body .footnotes li{position:relative}.markdown-body .footnotes li:target::before{position:absolute;top:-8px;right:-8px;bottom:-8px;left:-24px;pointer-events:none;content:\"\";border:2px solid #1f6feb;border-radius:6px}.markdown-body .footnotes li:target{color:#c9d1d9}.markdown-body .footnotes .data-footnote-backref g-emoji{font-family:monospace}.markdown-body .pl-c{color:#8b949e}.markdown-body .pl-c1,.markdown-body .pl-s .pl-v{color:#79c0ff}.markdown-body .pl-e,.markdown-body .pl-en{color:#d2a8ff}.markdown-body .pl-s .pl-s1,.markdown-body .pl-smi{color:#c9d1d9}.markdown-body .pl-ent{color:#7ee787}.markdown-body .pl-k{color:#ff7b72}.markdown-body .pl-pds,.markdown-body .pl-s,.markdown-body .pl-s .pl-pse .pl-s1,.markdown-body .pl-sr,.markdown-body .pl-sr .pl-cce,.markdown-body .pl-sr .pl-sra,.markdown-body .pl-sr .pl-sre{color:#a5d6ff}.markdown-body .pl-smw,.markdown-body .pl-v{color:#ffa657}.markdown-body .pl-bu{color:#f85149}.markdown-body .pl-ii{color:#f0f6fc;background-color:#8e1519}.markdown-body .pl-c2{color:#f0f6fc;background-color:#b62324}.markdown-body .pl-sr .pl-cce{font-weight:700;color:#7ee787}.markdown-body .pl-ml{color:#f2cc60}.markdown-body .pl-mh,.markdown-body .pl-mh .pl-en,.markdown-body .pl-ms{font-weight:700;color:#1f6feb}.markdown-body .pl-mi{font-style:italic;color:#c9d1d9}.markdown-body .pl-mb{font-weight:700;color:#c9d1d9}.markdown-body .pl-md{color:#ffdcd7;background-color:#67060c}.markdown-body .pl-mi1{color:#aff5b4;background-color:#033a16}.markdown-body .pl-mc{color:#ffdfb6;background-color:#5a1e02}.markdown-body .pl-mi2{color:#c9d1d9;background-color:#1158c7}.markdown-body .pl-mdr{font-weight:700;color:#d2a8ff}.markdown-body .pl-ba{color:#8b949e}.markdown-body .pl-sg{color:#484f58}.markdown-body .pl-corl{text-decoration:underline;color:#a5d6ff}.markdown-body g-emoji{display:inline-block;min-width:1ch;font-family:\"Apple Color Emoji\",\"Segoe UI Emoji\",\"Segoe UI Symbol\";font-size:1em;font-style:normal!important;font-weight:400;line-height:1;vertical-align:-.075em}.markdown-body g-emoji img{width:1em;height:1em}.markdown-body .task-list-item{list-style-type:none}.markdown-body .task-list-item label{font-weight:400}.markdown-body .task-list-item.enabled label{cursor:pointer}.markdown-body .task-list-item+.task-list-item{margin-top:4px}.markdown-body .task-list-item .handle{display:none}.markdown-body .task-list-item-checkbox{margin:0 .2em .25em -1.4em;vertical-align:middle}.markdown-body .contains-task-list:dir(rtl) .task-list-item-checkbox{margin:0 -1.6em .25em .2em}.markdown-body .contains-task-list{position:relative}.markdown-body .contains-task-list:focus-within .task-list-item-convert-container,.markdown-body .contains-task-list:hover .task-list-item-convert-container{display:block;width:auto;height:24px;overflow:visible;clip:auto}.markdown-body ::-webkit-calendar-picker-indicator{filter:invert(50%)}";
    }

    public String getSchemeCSS() {
        return isDarkCSS() ? getMarkDownGithubDarkCSS() : getMarkDownGithubLightCSS();
    }

    public String getMetaHtml() {
        return isDarkCSS() ? "<meta name=\"color-scheme\" content=\"dark\">" : "<meta name=\"color-scheme\" content=\"light\">";
    }

    public Boolean isDarkCSS() {
        EditorColorsScheme editorColorsScheme = EditorColorsManager.getInstance().getGlobalScheme();
        Color color = editorColorsScheme.getDefaultBackground();
        int brightness = (int) (0.3 * color.getRed() + 0.59 * color.getGreen() + 0.11 * color.getBlue());
        return brightness < 50;
    }


    public String getMarkdownHtml(String markdownText) {
        MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
                        AutolinkExtension.create(),
                        StrikethroughExtension.create(),
                        TaskListExtension.create(),
                        TablesExtension.create()
                ))
                .set(TablesExtension.WITH_CAPTION, false)
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.MIN_HEADER_ROWS, 1)
                .set(TablesExtension.MAX_HEADER_ROWS, 1)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true);
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(markdownText);
        String markdownHtml = renderer.render(document);


        String html = String.join("\n"
                , " <!DOCTYPE html>\n"
                , "<head>"
                , "     <title>Markdown Preview</title>"
                , "     <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                , getMetaHtml()
                , "     <style>"
                , getSchemeCSS()
                , "     </style>"
                , "     <style>"
                , "         .markdown-body {"
                , "              box-sizing: border-box;"
                , "              min-width: 200px;"
                , "              max-width: 980px;"
                , "              margin: 0 auto;"
                , "              padding: 45px;"
                , "          }"
                , "         @media (max-width: 767px) {"
                , "              .markdown-body {"
                , "                     padding: 15px;"
                , "               }"
                , "          }"
                , "     </style>"
                , "</head>"
                , "<body>"
                , "     <article class=\"markdown-body\">"
                , markdownHtml
                , "     </article>"
                , "</body>"
                , "</html>"

        );
        return html;
    }


    public static List<MarkdownTableModel> getMdTables(File file) {
        List<MarkdownTableModel> tableList = new ArrayList<>();
        try {
            String mdLines = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
                            TablesExtension.create()
                    ))
                    .set(TablesExtension.WITH_CAPTION, false)
                    .set(TablesExtension.COLUMN_SPANS, false)
                    .set(TablesExtension.MIN_HEADER_ROWS, 1)
                    .set(TablesExtension.MAX_HEADER_ROWS, 1)
                    .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                    .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                    .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                    .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true);
            Parser parser = Parser.builder(options).build();
            Node document = parser.parse(mdLines);
            NodeVisitor visitor = new NodeVisitor(
                    new VisitHandler<>(ThematicBreak.class, node -> {
                        FencedCodeBlock fencedCodeBlock = node.getPrevious() instanceof FencedCodeBlock ? (FencedCodeBlock) node.getPrevious() : null;
                        TableBlock tableBlock = Objects.requireNonNull(fencedCodeBlock).getPrevious() instanceof TableBlock ? (TableBlock) fencedCodeBlock.getPrevious() : null;
                        TableBody tableBody = (TableBody) Objects.requireNonNull(tableBlock).getLastChild();

                        if (tableBody == null) {
                            return;
                        }
                        tableList.add(new MarkdownTableModel() {{
                            setTitleRow(List.of(tableBody.getChars().unescape().split("\\|")));
                            setCodeBlockType(fencedCodeBlock.getInfo().unescape());
                            setCodeBlock(fencedCodeBlock.getContentChars().unescape());
                        }});
                    })
            );
            visitor.visit(document);
        } catch (Exception e) {
            log.error("getMdTables Error", e);
        }

        return tableList;
    }

}
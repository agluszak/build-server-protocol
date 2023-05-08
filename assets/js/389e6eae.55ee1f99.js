"use strict";(self.webpackChunk=self.webpackChunk||[]).push([[859],{3905:(e,t,r)=>{r.d(t,{Zo:()=>c,kt:()=>f});var n=r(7294);function o(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function a(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function i(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?a(Object(r),!0).forEach((function(t){o(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):a(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function s(e,t){if(null==e)return{};var r,n,o=function(e,t){if(null==e)return{};var r,n,o={},a=Object.keys(e);for(n=0;n<a.length;n++)r=a[n],t.indexOf(r)>=0||(o[r]=e[r]);return o}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(n=0;n<a.length;n++)r=a[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(o[r]=e[r])}return o}var l=n.createContext({}),p=function(e){var t=n.useContext(l),r=t;return e&&(r="function"==typeof e?e(t):i(i({},t),e)),r},c=function(e){var t=p(e.components);return n.createElement(l.Provider,{value:t},e.children)},u="mdxType",m={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},d=n.forwardRef((function(e,t){var r=e.components,o=e.mdxType,a=e.originalType,l=e.parentName,c=s(e,["components","mdxType","originalType","parentName"]),u=p(r),d=o,f=u["".concat(l,".").concat(d)]||u[d]||m[d]||a;return r?n.createElement(f,i(i({ref:t},c),{},{components:r})):n.createElement(f,i({ref:t},c))}));function f(e,t){var r=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var a=r.length,i=new Array(a);i[0]=d;var s={};for(var l in t)hasOwnProperty.call(t,l)&&(s[l]=t[l]);s.originalType=e,s[u]="string"==typeof e?e:o,i[1]=s;for(var p=2;p<a;p++)i[p]=r[p];return n.createElement.apply(null,i)}return n.createElement.apply(null,r)}d.displayName="MDXCreateElement"},7179:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>c,contentTitle:()=>l,default:()=>d,frontMatter:()=>s,metadata:()=>p,toc:()=>u});var n=r(7462),o=r(3366),a=(r(7294),r(3905)),i=["components"],s={id:"faq",title:"Frequently Asked Questions"},l=void 0,p={unversionedId:"overview/faq",id:"overview/faq",title:"Frequently Asked Questions",description:"Q: What's the relationship between BSP and LSP?",source:"@site/target/docs/overview/faq.md",sourceDirName:"overview",slug:"/overview/faq",permalink:"/build-server-protocol/docs/overview/faq",draft:!1,editUrl:"https://github.com/build-server-protocol/build-server-protocol/edit/master/docs/overview/faq.md",tags:[],version:"current",frontMatter:{id:"faq",title:"Frequently Asked Questions"},sidebar:"docs",previous:{title:"Specification",permalink:"/build-server-protocol/docs/specification"},next:{title:"Implementations",permalink:"/build-server-protocol/docs/overview/implementations"}},c={},u=[],m={toc:u};function d(e){var t=e.components,r=(0,o.Z)(e,i);return(0,a.kt)("wrapper",(0,n.Z)({},m,r,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("p",null,(0,a.kt)("strong",{parentName:"p"},"Q"),": What's the relationship between BSP and LSP?"),(0,a.kt)("p",null,(0,a.kt)("strong",{parentName:"p"},"A"),": They are complementary protocols. While LSP specifies endpoints for\ncommunication between an ",(0,a.kt)("strong",{parentName:"p"},"editor acting as client")," and language server, BSP\nspecifies endpoints between a ",(0,a.kt)("strong",{parentName:"p"},"language server acting as client")," and build\nserver. For example, in order to respond to a ",(0,a.kt)("inlineCode",{parentName:"p"},"textDocument/definition")," request\nfrom an editor client, a language server could query a build tool via BSP for\nthe classpath of a module."),(0,a.kt)("p",null,(0,a.kt)("strong",{parentName:"p"},"Q"),": What's the relationship between implementations of BSP and\nimplementations of LSP like\n",(0,a.kt)("a",{parentName:"p",href:"https://github.com/dragos/dragos-vscode-scala"},"dragos/dragos-vscode-scala"),",\n",(0,a.kt)("a",{parentName:"p",href:"https://marketplace.visualstudio.com/items?itemName=lampepfl.dotty"},"Dotty IDE"),"\nor ",(0,a.kt)("a",{parentName:"p",href:"https://github.com/scalameta/metals"},"Metals"),"?"),(0,a.kt)("p",null,(0,a.kt)("strong",{parentName:"p"},"A"),": Currently, those language servers each implement custom integrations for\neach supported build tool to extract build metadata. Those language servers\ncould instead implement a BSP client to extract build metadata from any build\ntools that implement BSP, sharing a single BSP server implementation. Likewise,\na new build tool could implement a BSP server and support a wide range of\nlanguage servers out-of-the-box."))}d.isMDXComponent=!0}}]);
Array.prototype.random = function() {
  return this[Math.floor(Math.random() * this.length)];
};

Vue.component( "MochiBox", {
  template: `
  <div class="MochiBox /" :class="[shiba, { pop: pop }]" @click="randomize" tabindex="0">
    <MochiShiba 
      :size="size"
      :mood="mood" 
      :leftEye="leftEye" 
      :rightEye="rightEye" 
      :leftEar="leftEar" 
      :rightEar="rightEar"
      :blush="blush"
    />
    <div class="MochiContent">
      <slot></slot>
    </div>
    <MochiPaws :size="size" />
  </div>
  `,
  props: {
    shiba: { type: String, default: "okaka" },
    size: { type: String, default: "medium" },
    mood: { type: String, default: "" },
    leftEye: { type: String, default: "open" },
    rightEye: { type: String, default: "open" },
    leftEar: { type: String, default: "up" },
    rightEar: { type: String, default: "flat" },
    blush: { type: Boolean, default: false },
    pop: { type: Boolean, default: true } 
  },
  mounted() {
    let _ = this;
    let time = 3000 + Math.random() * 2000;
    setTimeout(()=>{
      _.pop = false;
    },time);
  },
  methods: {
    randomize() {
      if ( this.canRandom ) {
        this.shiba = ["ume", "sesame", "tuna", "okaka", "anko", "kinako", "sakura", "monaka"].random();
        this.mood = ["", "happy", "content", "excited", "cheeky", "drool", "cute", "gleam"].random();
        this.leftEye = ["open", "wink", "shy", "laugh"].random();
        this.rightEye = ["open", "wink", "shy", "laugh"].random();
        this.leftEar = ["up", "down", "flat", "middle"].random();
        this.rightEar = ["up", "down", "flat", "middle"].random();
        this.blush = [true, false].random();
      }
    }
  },
  created() {
    if ( this.shiba === "random" ) {
      this.canRandom = true;
      this.randomize();
    }
  }
});
Vue.component( "MochiShiba", {
  
  template: `
    <div class="MochiShiba /" :class="styleClass">
      <svg class="shiba" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="152" height="174" viewBox="0 0 152 174">
      <title>Mochi Shiba</title>
      <defs>
        <radialGradient id="cheeks" cx="105.52" cy="57.11" r="6.94" gradientTransform="translate(3.23 14.14) rotate(4.8) scale(1 0.6)" gradientUnits="userSpaceOnUse">
          <stop offset="0" stop-color="#ff90ac"/>
          <stop offset="0.26" stop-color="#ff90ac" stop-opacity="0.99"/>
          <stop offset="0.4" stop-color="#fe91ac" stop-opacity="0.96"/>
          <stop offset="0.51" stop-color="#fe92ac" stop-opacity="0.91"/>
          <stop offset="0.6" stop-color="#fd93ac" stop-opacity="0.83"/>
          <stop offset="0.68" stop-color="#fc95ac" stop-opacity="0.73"/>
          <stop offset="0.76" stop-color="#fa97ac" stop-opacity="0.61"/>
          <stop offset="0.83" stop-color="#f89aad" stop-opacity="0.47"/>
          <stop offset="0.9" stop-color="#f69dad" stop-opacity="0.3"/>
          <stop offset="0.96" stop-color="#f4a1ad" stop-opacity="0.12"/>
          <stop offset="1" stop-color="#f2a3ad" stop-opacity="0"/>
        </radialGradient>
        <radialGradient id="cheeks-2" data-name="cheeks" cx="46.94" cy="57.11" r="6.94" gradientTransform="translate(77.16 7.47) rotate(85.2) scale(1 0.6)" xlink:href="#cheeks"/>
      </defs>
  </svg>
    </div>
  `,
  props: {
    size: { type: String, default: "medium" },
    shiba: { type: String, default: "okaka" },
    mood: { type: String, default: "" },
    leftEye: { type: String, default: "open" },
    rightEye: { type: String, default: "open" },
    leftEar: { type: String, default: "up" },
    rightEar: { type: String, default: "flat" },
    blush: { type: Boolean, default: false }
  },
  computed: {
    ears: function() {
      let l = "l4";
      let r = "r4";
      if ( this.leftEar === "up" ) { l = "l1"; }
      else if ( this.leftEar === "down" ) { l = "l3"; }
      else if ( this.leftEar === "flat" ) { l = "l2"; }
      if ( this.rightEar === "up" ) { r = "r1"; }
      else if ( this.rightEar === "down" ) { r = "r3"; }
      else if ( this.rightEar === "flat" ) { r = "r2"; }
      return `/ ears ${l} ${r}`;
    },
    eyes: function() {
      if ( this.leftEye === this.rightEye ) {
        return `/ eyes ${this.leftEye}`;
      } else {
        return `/ eyes l${this.leftEye} r${this.rightEye}`;
      }
    },
    cheeks: function() {
      return this.blush ? "/ blush" : "";
    },
    styleClass: function() {
      return `${this.size} / ${this.mood} ${this.eyes} ${this.ears} ${this.cheeks}`;
    }
  },
  methods: {},
});
Vue.component( "MochiPaws", {
  template: `
    <div class="MochiPaws /" :class="size">
      <svg class="paws" xmlns="http://www.w3.org/2000/svg" width="152" height="40" viewBox="0 0 152 40">
      <title>Mochi Paws</title>
    </svg>
    </div>
  `,
  props: {
    size: { type: String, default: "medium" }
  },
  computed: {},
  methods: {},
});



let app = new Vue({
  el: "#app",
  data: {}
});
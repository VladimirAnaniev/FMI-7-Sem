(() => {
   const section = document.querySelectorAll('section');
   const sections = {};

   Array.prototype.forEach.call(section, e => {
      sections[e.id] = e.offsetTop;
   });

   window.onscroll = () => {
      const scrollPosition = document.documentElement.scrollTop || document.body.scrollTop;

      for (let id in sections) {
         if (sections[id] <= scrollPosition) {
            const active = document.querySelector('.active');
            active && active.classList.remove('active');
            const newActive = document.querySelector('a[href*=' + id + ']');
            newActive && newActive.classList.add('active');
         }
      }
   };
})();

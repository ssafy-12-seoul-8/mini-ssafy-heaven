<template>
  <div @mouseenter="showScore=true" @mouseleave="showScore=false" class="group before:hover:scale-95 before:hover:h-full before:hover:w-full before:hover:h-11/12 before:hover:rounded-b-2xl before:transition-all before:duration-500 before:content-[''] before:w-full before:h-2/5 before:rounded-t-2xl before:bg-gradient-to-bl before:absolute before:top-0 w-11/12 h-1/3 relative bg-slate-50 flex flex-col items-center justify-center gap-2 text-center rounded-2xl overflow-hidden" :class="[from, via, to]">
    <img class="aspect-square h-1/2 bg-blue-700 mt-8 rounded-full border-4 border-slate-50 z-10 group-hover:scale-150 group-hover:-translate-x-40  group-hover:-translate-y-16 transition-all duration-500" :src="profileImage"/>
    <div class="z-10 group-hover:-translate-y-10 transition-all duration-500 pb-4">
      <span class="text-lg font-semibold">{{ nickname }}</span>
    </div>
    <div id="score-container">
        <p>{{ personalScore }}</p>
        <p>{{ roomScore }}</p>
      </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const showScore = ref(false)
const from = ref('')
const via = ref('')
const to = ref('')
const { profileImage, nickname, personalScore, roomScore, tier } = defineProps({
  profileImage: {
    type: String,
    default: 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBw8QEBIPDxAQDw8QEBUPDQ8PDw8NDw8PFRUWFhUVFRUYHSggGBolHRUVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OFhAPFS0dFRkrLS0tKy0rLSstKy0tLS0tKy0rLSstLSsrLSsrLS0tLSstLSstLS0tLS0tNy0tKy0rLf/AABEIAMkA+wMBIgACEQEDEQH/xAAbAAACAwEBAQAAAAAAAAAAAAADBAIFBgEAB//EADgQAAICAQMCBAMFBwQDAQAAAAECAAMRBBIhBTEiQVFhE3GBBjKRobEUI0JyweHwUmLR8TOSsoL/xAAYAQEBAQEBAAAAAAAAAAAAAAADAgEEAP/EAB8RAQEBAQADAQADAQAAAAAAAAABAhEDITESMkFhIv/aAAwDAQACEQMRAD8AweJNROAQiiLE1JRJqJ5RCqsRDqLCqs8iwyrMtVIiFhAskFhFWRaqRBVnWYCFxKjqupxIX8Gu6ionaOpA+cyttxJ7ztdpE9xnW7ovDRkLMjoNcQeZp9DqNwk2cXKPtndkMFnQszrQdk7thgs7tnus4AVkSkZ2yJWb17hN0i1iywdYras917hFlkCkZZZErNlZYXKQbrGysE6xc0VhJ1g2EadYFllxFAInMQhEiRKSGROSeJya88BCKJ4CERZzw1TRYVVnEWHRZfU8dRYZVnkWGVZNqpEQsIqyarJbZFq5AbiAJl+rWZJl11S3AMymquJM9HqEEhUqg1sh6rZqRUqIl/0ew8CVulwQc9vWWOgG0z1+NjT1DiTCwWltBEaGINKhtndsnkToE914LbOFYfbOFZ7r3CbrFrVj9gi1qz3XuEWWR2RkrI7Jsr1hYpBOscKQVixs0VhF1gHWOOsA6y5R2FGEgRDssGRLiKEROYkyJyUlMCGRYOsxmtZzOhNFhkWcRYZFlde4kiw6rOVrDosi1UjirOsvEKFnSnEi1UZjrJmWv7zZdYp7zK6irmXEaJgQ9SnODIlJb9KoDnkceYM2sWujt+BtrABbANxPmf8AT9Jb6/pyugvpXbgZuQdgP9Q9vX8fWVraYGxmVlyWOQ+duf1H0mp6ZW6KGAGOzLnejKe4JPrz6yf9XVRpVAGM8wd97A8NxJ9X0wqs4ytVnipcc45Ga29x+mIgaSBkOG54B4yJc4jtG/biCOTHaOpe8zz5zz8/7RjSIzZwM/LynvzKzta6qzcMwpEo9De6HxAgDuT2All04trG8OV06cWWdt/+1T+p/D2PWeLmilmqL2lEVmRP/IyqWAPnk+WIewS3u6g1JWvToiovGMAjHn5iA6lpwrEqNqsAygdgGAOB7ZMjUJm+1SVnNsOVnNsmVVhcrA2LHSsDYsbNFYrrFgHWO2LFnWJKOwm4gmEacQDCJB0EiQxCsJDEtCNTR6kytQxyhpz2HlWSCHRYvQ0drElaaLDqs4iwyrJtVI4FkwskFkwIdqopur05EyOqp5n0DV05EyvUtNgmXip3FDVVlgP7zRaRAgGduT/KMSt01fizieu1JDg4xgjnv+RlVMW5qTfuZkG7khc7hn18pcaJFTJCtYMZ8J2uPruExupv+JcwFhXJ4UnwEf0jKaG1ckEkDG1ksA/7no2tYzpaDp3Y7bea95Beqzkf57E+0yS2sjsjg7q224+Rnv2izOficrgnJ5ByB/WAv1ZtfIyzsd1jkYJlRKyHUAfIHj698RHUa5q84yMn8YGqrxcMD4QR885I9u5lhpFrsK2MoJqbJUkDJ7gzWC9D6XdqX/fFhX5rzkjPn6dsT6JUqVVitcIoGAMLj8DKHouqTBBG1uDkjsMf4ZYrrgCSzAgHGAMc/wDuf0kWN6MtOT95ME+SKCfyMN1UIG5OSFAwPZQPpJ6Kuizxll8PdckD6g4kdYaSWYHJLE4GO+eeQTI2vKnYe2JHbDMB5Tm2FC0FlgbFjZWBsWLmjqvtWK2LH7FitixZR2EnEAwjbiAcRYOlmEGRDMJDEsZVRDVmRVZ0CZqNzVjp3lnpzKTTvLXSvA0fNWaCHVYGgxpRBpY8BJAToEmBJqoiUyJnOu1YmmI4mP6/qCz7QeBPY+s38V9Kz2p0gPJIH6wlK4A/ziB11mB6xhqXUV7bQwOcn3EsLbyeOUyOy+Ie/wDmYoiEtvY8L29BHtDWGBzyMc9u3rnynnirggjee4O1xkgjGME+ZHcZ5GAPTBelJhjn7ljFFYdgcHGfxEcapRTYvDYGVOVI+REW6eh/ZkAGW+KHAzzjnGfyldS7otIG3F2wlTeIjuxJwAv5mMIxFhKhh2BBHLNiM/ZPTm25VcAoWe18nucY7e0dq0IufU2l9q1WlSFOM8+X4YmxNG6Xr+cEY3fe4xn5e0tNX8LYz77MfdARlIznOcDH1lNUhK52sFH3O27y7niQ1DsyYPCswDg+vynq2LjoHUq1aw7i6its5A4PkAY104MVJxwSTnnOT6xfo/TKUB2oF3YJYeLP49pePXtTAPHpI1zlbL2wniexJ4ncTljqoZEBYI0RA2CNBUjYsUtEftEUtESIpGwQDiNWCLuIsFSziDxDOIPEWDoNYkmSdrWFKzdIyAhxLHTPEGWH07QNR0ZrQaV5Y1yl0lkttO059HyYAkwJ5RJgQ7SQtrX2oflMFqWLOT7zddVH7tvlMSFHPzieIfkdsGFBlXqrPMnJl2hBGP1lY9Ck4YbSPMHvEQr97Hj7o9YWzShBWzFmRrNrknjgeEfLMsP2RWUrkZ8iYrpdT8Msli70PD1t6e3vMeVGmZ/is33AgIPcDIJxgdsYmn09QPwWTwi1AWTuFbjI494jrtLpfhfEqNoOf/EzZUH54lr9l9ObDWr4GD4R2wM5/wA+U91vGv6P9n1YKQfhgIzsw4wARuImJ6r1kUOVoOyi194ZgzWFGyTafwzjv2+n0Xr2pGl0+0A7LSKH2nxBGILfkG/GZmz7I2XLVbsrtVVArc3mgsg4UWL54AHY84m9/v8ApEhmuwp8egmvUGtQ6X0DItqPd9hz29okvwXRghBzg4+7znPYiN6KyvSF3d1u1NgCv8MBq60B+4PqYOzQPaGvRSoJJGBjiVn/AFlWHQm24VgCPXP3Zc6ogcDGJQdNL4wynI8+RLe2ttoJGM/pJ29n6GBO4klE7iccrsqBEBYI0RAWCNBUnYIpaI9YIpaIkRSNgiziN2CLOI2RUs4gsQzwRiwdRrEKBIViHAiaFC7rIpwYd1g9sDR8ntM8uNLZM/S2JaaWyBqGzV9SYcCI6Z4/XObToyR6wv7s/KYdsAHPrN91RR8M59J896icHaATzF8V9D8n1DTava+D2Me1Wi38r3lE9Dnkjj07RrRdUanCsmc8KBzj0+sUTy6G0NzjB/3qv9ZZ2dEDKLFJx/GAwfxDy4zLCrTV2rvPDY9eYfQO1RKuCa2P3uM5+QH5mTVRkkr+I2zDDBHB+83p/WXX2b11aWEs+1lxtG1jgDjnjg+0tB0sfGFgG4A7hnafynPtJ07Tsa7VPwby6oTgEOhYZyPXjv8AOZ33xvFxqNQNbXsRw2VLjPGCvsfciVfUNC1qgVeJ61w1YJyCPMDM23StJpatJ8OitKSy4LBQGJPclgD3PnPdI6JUp+K2Ny9mFgYY8s8Dy/WVNQdjDdO6aAvxL1cAfwbSM+2Se3HkJpdFr67E28KBwU9APL5Tn2n1q2N8GsDaM7eG8XkSD/YxTpfQWOGOR/tOO3zGP0ErvZ7SuqGSzCqvAPJkepDsBjAktTqK9MnIOf8AbjP4RFbvieLkA8894e/4k8c/6joE7iTE9icsdVQIgLBGSICwRoKlLBE7RHrBE7REiKStEVsjdoitkbItFngTDvAmLB1ymMARXTmOKIgUGEGRGGEGRC0bKCiN6d4sBCIcQrCSrvSWS207TPaWyXOlsnL5I6cUTqy/u2PoJgbhkk+8+ha1d1bD2nzvWg7iBK8Pys8v0vZaBwBn6TyjBLEZOcIPQev5YkqqMckxliOMkD6dx6iMIDTPZUWck89lJwGx/n5iX3S+q5wl2A5yT2woXufpgj6H0BIKNKHJ5BKjahx/Fnv+RMWs0bBnwMYQIvyLKv8A85mPNRVqE27wQuSB/b9JJ7dPuAcDJPfgjMqqtFuH1zj3zmTOj9e8n01f16kLgIxAGRknscYGPTnEauZrOWsO0A5UHCsuPEG9e5OfaYPXvcjqcnAIJ/lGCfyBP0EY6Lqr7GFYY87HPn4QFUg/+2fxlSIra6apK0IQbskbP/12Pt6fSGV9UOQilfLnmN9O0SIuCckHOfPB5AhtQQoOPEPbuMTOpVH7ZvJSxCD6N2+hnWoC4I7eknfVu8QOQeR7TwztwZPk/iTx/wAg8TuJ2enLHXUWi9kZaL2RoGlbIpbHLIpbEiKStilkbtilkfI9FngjDPAmLBUrpXlnVKbTNLfTtLg6KwgmEORBsJGl5DAksTkkIdJBaWxLjR2yjEe0dsHeS4rQE5Uj2mK11G12HnnvNhQ+RKXqdHiLDkwvF6thPJ7krOXNg4GMjvnynNLU2d3J5GAFOTz6TrVHOe/MYe0hOCcngkcAx4Gr7oumLjBYqQxPPbvjkH5GPdT6aUUnHcL288Hv+ErvszuZkO/DYwVyCDz/AMETZdR0m6g+W3nI/An8CZOvVZGb0QwM4+QljVpt3OIpoRxg9wZfaTAXMmr6zvXel5rJA5A+pgPsxoNjFu/AU4Iydu0enHaafUWBh7ygCmslQQM8eHBxk5GT5djKnxNaWx68cgZPluweM/8AM5SiY8DNnzRjkzL33WsyhS5Zc8N/F2+7284fSa7a2CcHuV55z6Z7GV+U9XlVWCV575E7qO/aE0pyu+AtOTA8t9G8U7UJ6cnYGXRpFovZGGi9kaBpeyJ3Ru2J3RIik7YpZGrYpZGyPRd4IwjwRiwVVdBltpWlLSZZ6RpUTVqJB5Ks8Tjz1ZkKSEiTOgw6WOmTofBgiZxW5kWLlaLR2cRTW27X9QfKS6c8N1GjjcPKc09adF95Z/qtYB9POLI4ZcKoyO54Aj3UlLkMP9OD/N/1JdD6cmfGC7HsvJIJ7HzxHl4CxzpWnZLVLWMUz3DkD6EHHr5zepqcVGtm3gjCknnHvEtF9k8OGLgk8EE52j0HH6y81/2cPwttZGfLjz9ZmrKnjPabT8kw9lu0YhtKmAQe44PzifVKzjiDNf8AXD/n10vqC9i7a2KknBI4P4x7p/QiQEzyOSTyD8/X5mV3Qw5sHBIB547TfaVxgZxn+su2wdjI2dHvF4GwuqDxOo45xg+QJ7+/HvHdd0/cniXLDs3cj0I8/pNrQYp1XS5GVH8w8vnN/SeMxWhSsKe/eLtHNbZk4HYcCJNOby67XV4s8jk9OT0nK9ONF7IZjAWGNA0vZE7jG7DErjFiKUtMUsMZtMUsMbI9AOYIwjmCJiwVUtJljpmlVUY/p2mpXlDcSTmA0zQlhm1M+oEz2ZEmeJh0sdLSAbmQd5PTISZGlxd9MEvTVuXEq+m09poKK+JweTXt24z6ZJ9N+9+GezkD5H1ml6F0yujdwCxOS2OWPl/SKdW0ePF6cyx0moDBW9ufnF/X6zAazytJoyMYOOJbjGJnuneLk9yTLipiBgnPoZsHWWsGGb+Y/rF9SmRGtSuHYZzyefWBbtBv10z4r+n6v4T7cYBPfzmp6fqQxxgesxuur5z7y66brsEfwnAPbvF/0NbSrtFurawVpj+JuPkItVrScYBIxyZW9TJJ3HzP4TdXkTmdqusMCxk3METOWu2PZnMzmZ6VlOkXMXcwzmL2GPkNAtMTuMZtMSuMWIpW0xWww9pithjZFQnMFJuYOJBVQ1mO0NEKzG6TNjF1pWh3aJ6ZoV3lVM+pbpxmgt8iXh0kFRMmW2gpEqaWlvobJzeS10+ORodFWJb0gSl0lssE1E4NS9dk+J9SUFTKHputCWGtjgHsT2BEuLbcjEqLunbmzOnw/wAeVzeX60nTdaDjDAgHkg5Es+pa0jARgc8kjuJQ9L6ZtAj+pqCCVZ69Dn0uWnN0TN858eDyuhO+sGO9N0wzK740sun3xci3Gj06ACV3VhHtPZxKzrFwwZuvcHj6qXaBLQD3QRunPcuyU3unt0TN0ib5Wcp1TTtFrHgnvi1l8fMDqiWvErnnLb4pbbFkHa5a0Wsaessi72RZB2usYPMgzyG+WNTIY1SYqkZqlRNWNDyT2wNM48qpgnxZH4kGJ6HSQzTZLPS24lRXH9PA1OnzeL2nWY84wNd7ykSFWD+IX91eU6vPnLXSuDiZnS95f6PynucTb1o9IRiJ9ZuAUxjSdpV9f+6ZsT/ahbUz37TEp6e/MX+jw1EY0+u2mVUkJvGWtZT1oAd5WdR6nvPeVIg2nuMhhtRBnUQBgzPfmK/VM/tEidRFpFpszGXVFfUQD3wbQTRJB2uvbAPbPPAPLkRa49kAzyTwLS5EWvF5HfImRlMf/9k=',
  },
  nickname: {
    type: String,
    required: true,
  },
  personalScore: {
    type: Number,
    required: true,
  },
  roomScore: {
    type: Number,
    required: true,
  },
  tier: {
    type: Object,
    required: true,
  }
})

onMounted(() => {
  from.value = `from-[${tier.from}]`;
  via.value = `via-[${tier.via}]`;
  to.value = `to-[${tier.to}]`;
})
</script>

<style scoped>
@keyframes show-up {
  0% {
    transform: translateY(0);
  }

  100% {
    transform: translateY(-1rem);
  }
}

#score-container {
  position: absolute;
  transition: top 0.5s;
  top: 100%;
}

.group:hover #score-container {
  top: 70%;
}
</style>

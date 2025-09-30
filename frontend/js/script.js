 // Sistema de navegação
 const navLinks = document.querySelectorAll('.nav-link');
 const sections = document.querySelectorAll('.content-section');

 navLinks.forEach(link => {
     link.addEventListener('click', (e) => {
         e.preventDefault();
         
         // Remove active class from all links
         navLinks.forEach(l => l.classList.remove('active'));
         // Add active class to clicked link
         link.classList.add('active');
         
         // Hide all sections
         sections.forEach(section => section.classList.remove('active'));
         // Show target section
         const targetSection = document.getElementById(link.dataset.section);
         targetSection.classList.add('active');
     });
 });

 // Sistema de pontos (simulação)
 let userPoints = 2847;
 const pointsDisplay = document.getElementById('user-points');
 
 // Função para atualizar pontos
 function updatePoints(newPoints) {
     userPoints = newPoints;
     pointsDisplay.textContent = userPoints.toLocaleString();
     updateRewardButtons();
 }

 // Atualizar botões de recompensa baseado nos pontos
 function updateRewardButtons() {
     const rewardCards = document.querySelectorAll('.reward-card');
     
     rewardCards.forEach(card => {
         const costElement = card.querySelector('.reward-cost');
         const button = card.querySelector('.btn-redeem');
         const cost = parseInt(costElement.textContent.replace(/\D/g, ''));
         
         if (userPoints >= cost) {
             button.disabled = false;
             button.textContent = 'Resgatar';
         } else {
             button.disabled = true;
             button.textContent = 'Pontos Insuficientes';
         }
     });
 }

 // Event listeners para botões de recompensa
 document.querySelectorAll('.btn-redeem').forEach(button => {
     button.addEventListener('click', (e) => {
         if (!button.disabled) {
             const card = button.closest('.reward-card');
             const title = card.querySelector('.reward-title').textContent;
             const cost = parseInt(card.querySelector('.reward-cost').textContent.replace(/\D/g, ''));
             
             if (confirm(`Confirma o resgate de "${title}" por ${cost} pontos?`)) {
                 updatePoints(userPoints - cost);
                 showNotification(`${title} resgatado com sucesso!`);
             }
         }
     });
 });

 // Sistema de notificações
 function showNotification(message) {
     const notification = document.createElement('div');
     notification.style.cssText = `
         position: fixed;
         top: 20px;
         right: 20px;
         background: linear-gradient(45deg, #4ecdc4, #44a08d);
         color: white;
         padding: 1rem 2rem;
         border-radius: 10px;
         box-shadow: 0 5px 15px rgba(0,0,0,0.3);
         z-index: 10000;
         animation: slideIn 0.5s ease;
     `;
     notification.textContent = message;
     
     document.body.appendChild(notification);
     
     setTimeout(() => {
         notification.style.animation = 'slideOut 0.5s ease';
         setTimeout(() => {
             document.body.removeChild(notification);
         }, 500);
     }, 3000);
 }

 // Adicionar animações CSS dinamicamente
 const style = document.createElement('style');
 style.textContent = `
     @keyframes slideIn {
         from { transform: translateX(100%); opacity: 0; }
         to { transform: translateX(0); opacity: 1; }
     }
     @keyframes slideOut {
         from { transform: translateX(0); opacity: 1; }
         to { transform: translateX(100%); opacity: 0; }
     }
 `;
 document.head.appendChild(style);

 // Event listeners para botões de curso
 document.querySelectorAll('.course-card').forEach(card => {
     card.addEventListener('click', (e) => {
         // Se clicou em um botão, não processa o clique do card
         if (e.target.tagName === 'BUTTON') return;
         
         const title = card.querySelector('.course-title').textContent;
         showNotification(`Abrindo curso: ${title}`);
     });
 });

 // Menu mobile (funcionalidade básica)
 const mobileMenuBtn = document.querySelector('.mobile-menu-btn');
 const navMenu = document.querySelector('.nav-menu');

 mobileMenuBtn.addEventListener('click', () => {
     navMenu.style.display = navMenu.style.display === 'flex' ? 'none' : 'flex';
 });

 // Inicializar estado dos botões de recompensa
 updateRewardButtons();

 // Simulação de progresso em tempo real (opcional)
 setInterval(() => {
     const progressBars = document.querySelectorAll('.progress-fill');
     progressBars.forEach(bar => {
         const currentWidth = parseInt(bar.style.width) || 0;
         if (currentWidth < 100 && Math.random() > 0.98) {
             bar.style.width = Math.min(currentWidth + 1, 100) + '%';
         }
     });
 }, 10000);


 class Carousel {
    constructor() {
        this.currentSlide = 0;
        this.track = document.getElementById('carouselTrack');
        this.prevBtn = document.getElementById('prevBtn');
        this.nextBtn = document.getElementById('nextBtn');
        this.indicators = document.querySelectorAll('.indicator');
        this.totalSlides = document.querySelectorAll('.carousel-slide').length;
        
        this.init();
    }
    
    init() {
        this.updateCarousel();
        this.bindEvents();
    }
    
    updateCarousel() {
        // Move track
        const translateX = -this.currentSlide * 100;
        this.track.style.transform = `translateX(${translateX}%)`;
        
        // Update indicators
        this.indicators.forEach((indicator, index) => {
            indicator.classList.toggle('active', index === this.currentSlide);
        });
        
        // Update button states
        this.prevBtn.disabled = this.currentSlide === 0;
        this.nextBtn.disabled = this.currentSlide === this.totalSlides - 1;
    }
    
    bindEvents() {
        this.prevBtn.addEventListener('click', () => this.prevSlide());
        this.nextBtn.addEventListener('click', () => this.nextSlide());
        
        this.indicators.forEach((indicator, index) => {
            indicator.addEventListener('click', () => this.goToSlide(index));
        });
        
        // Touch/swipe support
        let startX = 0;
        let currentX = 0;
        let isDragging = false;
        
        this.track.addEventListener('touchstart', (e) => {
            startX = e.touches[0].clientX;
            isDragging = true;
        });
        
        this.track.addEventListener('touchmove', (e) => {
            if (!isDragging) return;
            currentX = e.touches[0].clientX;
        });
        
        this.track.addEventListener('touchend', () => {
            if (!isDragging) return;
            isDragging = false;
            
            const diffX = startX - currentX;
            if (Math.abs(diffX) > 50) {
                if (diffX > 0) {
                    this.nextSlide();
                } else {
                    this.prevSlide();
                }
            }
        });
    }
    
    prevSlide() {
        if (this.currentSlide > 0) {
            this.currentSlide--;
            this.updateCarousel();
        }
    }
    
    nextSlide() {
        if (this.currentSlide < this.totalSlides - 1) {
            this.currentSlide++;
            this.updateCarousel();
        }
    }
    
    goToSlide(index) {
        this.currentSlide = index;
        this.updateCarousel();
    }
}

// Initialize carousel when page loads
document.addEventListener('DOMContentLoaded', () => {
    new Carousel();
});
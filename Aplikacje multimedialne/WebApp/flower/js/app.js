const video = document.getElementById('video');
const startCameraButton = document.getElementById('start-camera');
const takePhotoButton = document.getElementById('take-photo');
const canvas = document.getElementById('canvas');
const photoCountDisplay = document.getElementById('photo-count');
const imageContainer = document.getElementById('image-container');
const processedMedia = document.getElementById('processedMedia');

const startAudioButton = document.getElementById('start-audio');
const audioContainer = document.getElementById('processedMedia');
let mediaRecorder;
let audioChunks = [];
let isRecording = false; 
let audioStream; 

const startProcessButton = document.getElementById('start-process');



// Modal
const modal = document.getElementById('camera-modal');
const closeModal = document.getElementsByClassName('close')[0];

// Zmienna do przechowywania liczby zdjęć
let photoCount = 0;

// Uruchomienie kamery
startCameraButton.addEventListener('click', async () => {
    const stream = await navigator.mediaDevices.getUserMedia({ video: true });
    video.srcObject = stream;
    modal.style.display = 'block'; 
});

// Przycisk zrób zdjęcie
takePhotoButton.addEventListener('click', () => {
    canvas.width = video.videoWidth;
    canvas.height = video.videoHeight;
    const context = canvas.getContext('2d');
    context.drawImage(video, 0, 0);
    
    const img = document.createElement('img');
    img.src = canvas.toDataURL('image/png');
    imageContainer.appendChild(img);

     // Licznik zdjęć +1
     photoCount++;
     photoCountDisplay.textContent = photoCount; 
 });

// Zamykanie modalu
closeModal.addEventListener('click', () => {
    modal.style.display = 'none'; 
    const stream = video.srcObject;
    if (stream) {
        const tracks = stream.getTracks();
        tracks.forEach(track => track.stop()); 
    }
});

// Zamykanie modalu klikając w tło
window.addEventListener('click', (event) => {
    if (event.target == modal) {
        modal.style.display = 'none'; 
        const stream = video.srcObject;
        if (stream) {
            const tracks = stream.getTracks();
            tracks.forEach(track => track.stop());
        }
    }
});

// Nagrywanie dzwięku
startAudioButton.addEventListener('click', async () => {
    try {
        if (!audioStream) {
            // Jeden raz będzie pytać o dostęp do nagrywania dzwięku
            audioStream = await navigator.mediaDevices.getUserMedia({ audio: true });
            mediaRecorder = new MediaRecorder(audioStream);

            mediaRecorder.ondataavailable = (event) => {
                audioChunks.push(event.data);
            };

            mediaRecorder.onstop = () => {
                const audioBlob = new Blob(audioChunks, { type: 'audio/mp3' });
                audioChunks = [];

                const audioUrl = URL.createObjectURL(audioBlob);
                const audioElement = document.createElement('audio');
                audioElement.controls = true;
                audioElement.src = audioUrl;
                audioContainer.appendChild(audioElement);
            };
        }

        if (!isRecording) {
            mediaRecorder.start();
            isRecording = true;
            alert('Nagrywanie dźwięku rozpoczęte. Kliknij ponownie, aby zakończyć nagrywanie.');
        } else {
            mediaRecorder.stop();
            isRecording = false;
            alert('Nagrywanie zakończone');
        }

    } catch (error) {
        console.error('Błąd podczas nagrywania dźwięku:', error);
        alert('Nie udało się uzyskać dostępu do mikrofonu.');
    }
});

// Dodanie kolorowej ramki do zdjęć
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

startProcessButton.addEventListener('click', () => {
    const randomColor = getRandomColor();

    const images = imageContainer.getElementsByTagName('img');
    for (let img of images) {
        img.style.border = `5px solid ${randomColor}`; 
    }
});
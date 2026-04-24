from fastapi import FastAPI, UploadFile, File
from PIL import Image
import torch
import io
import os
import torch.nn.functional as F

from src.model.cnn import MnistCNN
from src.utils.preprocess import preprocess_image

BASE_DIR = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

app = FastAPI()

model = MnistCNN()
model.load_state_dict(torch.load(
    os.path.join(BASE_DIR, "models", "mnist_cnn.pt"),
    map_location=torch.device('cpu')
))
model.eval()

@app.get("/health")
async def health():
    return {"status": "ok"}

@app.post("/predict")
async def predict(file: UploadFile = File(...)):
    try:
        image_data = await file.read()
        image = Image.open(io.BytesIO(image_data))
    except Exception:
        return {
            "error": "Invalid image file"
            }
    
    # Prétraiter l'image
    image_tensor = preprocess_image(image)
    
    # Faire la prédiction
    with torch.no_grad():
        output = model(image_tensor)
        predicted_class = torch.argmax(output, dim=1).item()
        
    probs = F.softmax(output, dim=1)
    confidence = probs[0][predicted_class].item()
    
    return {
        "predicted_class": predicted_class,
        "confidence": confidence
        }


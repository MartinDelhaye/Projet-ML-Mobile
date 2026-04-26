import cv2
import numpy as np
from PIL import Image

def center_digit(digit_img):
    h, w = digit_img.shape
    size = max(h, w)
    square = np.zeros((size, size), dtype=np.uint8)
    y_offset = (size - h) // 2
    x_offset = (size - w) // 2
    square[y_offset:y_offset+h, x_offset:x_offset+w] = digit_img
    return square

def segment_image(image):
    image_np = np.array(image)
    gray = cv2.cvtColor(image_np, cv2.COLOR_RGB2GRAY)
    
    _, thresh = cv2.threshold(gray, 128, 255, cv2.THRESH_BINARY)
    contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    contours = sorted(contours, key=lambda c: cv2.boundingRect(c)[0])
    
    min_area = thresh.shape[0] * thresh.shape[1] * 0.01
    contours = [c for c in contours if cv2.contourArea(c) > min_area]
    
    if len(contours) <= 1:
        return [center_digit(thresh)]
    
    padding = int(min(thresh.shape[0], thresh.shape[1]) * 0.02)
    digits = []
    for contour in contours:
        x, y, w, h = cv2.boundingRect(contour)
        x = max(0, x - padding)
        y = max(0, y - padding)
        w = min(thresh.shape[1] - x, w + 2 * padding)
        h = min(thresh.shape[0] - y, h + 2 * padding)
        digit = thresh[y:y+h, x:x+w]
        
        kernel = np.ones((2,2), np.uint8)
        digit = cv2.erode(digit, kernel, iterations=1)
        
        digits.append(center_digit(digit))
    
    return digits
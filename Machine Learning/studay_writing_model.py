
##Mengambil Dataset Dari Drive
"""

from google.colab import drive
drive.mount('/content/drive/')

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import tensorflow as tf
import seaborn as sns

from tensorflow.keras.callbacks import EarlyStopping, ReduceLROnPlateau,ModelCheckpoint
from keras.callbacks import ModelCheckpoint, EarlyStopping, ReduceLROnPlateau
from sklearn.model_selection import train_test_split

testing_letter = pd.read_csv('/content/drive/MyDrive/Dataset/emnist-balanced-test.csv')
training_letter = pd.read_csv('/content/drive/MyDrive/Dataset/emnist-balanced-train.csv')

data = {
    "Dataset": ["Training Letter", "Testing Letter"],
    "Jumlah gambar dan kolom": [str(training_letter.shape), str(testing_letter.shape)],

}

df_penjelasan = pd.DataFrame(data)

print("\nTabel Penjelasan:")
print(df_penjelasan)

#training_letters
y1 = np.array(training_letter.iloc[:,0].values)
x1 = np.array(training_letter.iloc[:,1:].values)
#testing_labels
y2 = np.array(testing_letter.iloc[:,0].values)
x2 = np.array(testing_letter.iloc[:,1:].values)
print('label pada y2 dan x2')
print(y1.shape)
print(x1.shape)

import matplotlib.pyplot as plt
fig,axes = plt.subplots(3,5,figsize=(10,8))
for i,ax in enumerate(axes.flat):
    ax.imshow(x1[i].reshape([28,28]))

number_of_classes = 37

train_images = x1 / 255.0
test_images = x2 / 255.0

train_images_number = train_images.shape[0]
train_images_height = 28
train_images_width = 28
train_images_size = train_images_height*train_images_width

train_images = train_images.reshape(train_images_number, train_images_height, train_images_width, 1)

test_images_number = test_images.shape[0]
test_images_height = 28
test_images_width = 28
test_images_size = test_images_height*test_images_width

test_images = test_images.reshape(test_images_number, test_images_height, test_images_width, 1)

print("Nilai maksimum di y1:", y1.max())
print("Nilai maksimum di y2:", y2.max())
print("Nilai minimum di y1:", y1.min())
print("Nilai minimum di y2:", y2.min())

invalid_labels_y1 = y1[y1 >= number_of_classes]
invalid_labels_y2 = y2[y2 >= number_of_classes]
print("Label tidak valid di y1:", invalid_labels_y1)
print("Label tidak valid di y2:", invalid_labels_y2)


valid_indices_y1 = np.where(y1 < number_of_classes)[0]
y1 = y1[valid_indices_y1]
train_images = train_images[valid_indices_y1]
valid_indices_y2 = np.where(y2 < number_of_classes)[0]
y2 = y2[valid_indices_y2]
test_images = test_images[valid_indices_y2]

number_of_classes = max(len(np.unique(y1)), len(np.unique(y2)))

y1 = tf.keras.utils.to_categorical(y1, number_of_classes)
y2 = tf.keras.utils.to_categorical(y2, number_of_classes)

print("Shape train_images:", train_images.shape)
print("Shape test_images:", test_images.shape)
print("Shape y1:", y1.shape)
print("Shape y2:", y2.shape)

train_x,test_x,train_y,test_y = train_test_split(train_images,y1,test_size=0.2,random_state = 42)

model = tf.keras.Sequential([
    tf.keras.layers.Input(shape=(28, 28, 1)),
    tf.keras.layers.Conv2D(32, 3, activation='relu'),
    tf.keras.layers.MaxPooling2D(2, 2),
    tf.keras.layers.Flatten(),
    tf.keras.layers.Dense(512, activation='relu'),
    tf.keras.layers.Dense(number_of_classes, activation='softmax')
])

model.compile(optimizer='Adam',
              loss='categorical_crossentropy',
              metrics=['accuracy'])

model.summary()

MCP = ModelCheckpoint('Best_points.keras', verbose=1, save_best_only=True, monitor='val_accuracy', mode='max')
ES = EarlyStopping(monitor='val_accuracy', min_delta=0, verbose=0, restore_best_weights=True, patience=3, mode='max')
RLP = ReduceLROnPlateau(monitor='val_loss',
                        patience=3, factor=0.2,
                        min_lr=0.0001
                        )


history = model.fit(train_x,train_y,epochs=20,validation_data=(test_x,test_y),callbacks=[MCP,ES,RLP])

q = len(history.history['accuracy'])

plt.figsize=(10,10)
sns.lineplot(x = range(1,1+q),y = history.history['accuracy'], label='Accuracy')
sns.lineplot(x = range(1,1+q),y = history.history['val_accuracy'], label='Val_Accuracy')

sns.lineplot(x=range(1, 1 + q), y=history.history['loss'], label='Loss', linestyle='--')
sns.lineplot(x=range(1, 1 + q), y=history.history['val_loss'], label='Val_Loss', linestyle='--')

plt.xlabel('epochs')
plt.ylabel('Accuray')

num_images_to_test = 20
random_indices = np.random.choice(test_x.shape[0], num_images_to_test, replace=False)
sample_images = test_x[random_indices]
true_labels = test_y[random_indices]
predictions = model.predict(sample_images)

fig, axes = plt.subplots(4, 5, figsize=(15, 6))
axes = axes.flatten()
for i in range(num_images_to_test):
    axes[i].imshow(sample_images[i].reshape(28, 28), cmap='gray')
    axes[i].set_title(f"Pred: {np.argmax(predictions[i])}\nTrue: {np.argmax(true_labels[i])}")
    axes[i].axis('off')

plt.tight_layout()
plt.show()

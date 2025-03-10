from aiogram.types import (ReplyKeyboardMarkup, KeyboardButton, 
                           InlineKeyboardMarkup, InlineKeyboardButton)

main = ReplyKeyboardMarkup(keyboard=[[KeyboardButton(text='/Rejestracja')],
                                     [KeyboardButton(text='Katalog'),
                                     KeyboardButton(text='Koszyk')],
                                     [KeyboardButton(text='Kontakty'),
                                     KeyboardButton(text='O nas')]],
                            resize_keyboard=True,
                            input_field_placeholder='Proszę wybrać punkt w menu...')

catalog = InlineKeyboardMarkup(inline_keyboard=[
    [InlineKeyboardButton(text='Koszulki', callback_data='t-shirt')],
    [InlineKeyboardButton(text='Adidasy', callback_data='sneakers')],
    [InlineKeyboardButton(text='Czapki', callback_data='cap')]])

get_number = ReplyKeyboardMarkup(keyboard=[[KeyboardButton(text='Wysłać numer', request_contact = True)]],
                                resize_keyboard=True )

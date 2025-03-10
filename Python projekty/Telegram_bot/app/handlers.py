from aiogram import F, Router
from aiogram.types import Message, CallbackQuery
from aiogram.filters import CommandStart, Command
from aiogram.fsm.state import State, StatesGroup
from aiogram.fsm.context import FSMContext

import app.keyboards as kb

router = Router()

class Register(StatesGroup):
    name= State()
    age=State()
    number=State()

@router.message(CommandStart())
async def cmd_start(message: Message):
    await message.reply('Cześć!', reply_markup=kb.main)
    await message.answer('Jak sprawy?')

@router.message(Command('help'))
async def cmd_help(message:Message):
    await message.answer('Przycisk pomocy został nacisnięty')

@router.message(F.text == "Wszystko w porządku")
async def nice(message:Message):
    await message.answer('Cieszę się')

@router.message(F.text == "Katalog")
async def catalog(message:Message):
    await message.answer('Proszę wybrać kategorię towaru', reply_markup=kb.catalog)

@router.callback_query(F.data =='t-shirt')
async def t_shirt(callback: CallbackQuery):
    await callback.answer('Kategoria została wybrana', show_alert=True)
    await callback.message.answer('Wybrana została kategoria koszulek.')


@router.message(Command('Rejestracja'))
async def register(message:Message, state=FSMContext):
    await state.set_state(Register.name)
    await message.answer('Proszę podać imię')

@router.message(Register.name)
async def register_name(message:Message, state:FSMContext):
    await state.update_data(name=message.text)
    await state.set_state(Register.age)
    await message.answer('Proszę podać wiek')

@router.message(Register.age)
async def register_age(message:Message, state:FSMContext):
    await state.update_data(age=message.text)
    await state.set_state(Register.number)
    await message.answer('Proszę podać numer telefona', reply_markup=kb.get_number)

@router.message(Register.number, F.contact)
async def register_number(message:Message, state = FSMContext):
    await state.update_data(number=message.contact.phone_number)
    data = await state.get_data()
    await message.answer(f'Imię: {data["name"]}\nWiek: {data["age"]}\nNumer: {data["number"]}')
    await state.clear